package com.eop.userservice.service.impl;

import com.eop.baseservice.common.constant.UserRole;
import com.eop.baseservice.common.constant.UserStatus;
import com.eop.baseservice.common.dto.user.request.CreateUserRequest;
import com.eop.baseservice.common.dto.user.request.UpdateUserPasswordRequest;
import com.eop.baseservice.common.dto.user.request.UpdateUserRequest;
import com.eop.baseservice.common.dto.user.response.UserResponse;
import com.eop.baseservice.common.response.PagingRequest;
import com.eop.baseservice.config.EopProperties;
import com.eop.baseservice.entity.EopConfig;
import com.eop.userservice.entity.User;
import com.eop.userservice.repository.UserRepository;
import com.eop.userservice.service.UserPersonService;
import com.eop.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserPersonService userPersonService;
    private final EopProperties eopConfig;

    @Override
    public void validateIdExists(String id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }
    }

    @Override
    public void validateBkNotExists(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username found! please use other");
        }
    }

    @Override
    public void validateBkNotChange(String oldUsername, String currUsername) {
        if (!oldUsername.equalsIgnoreCase(currUsername)) {
            validateBkNotExists(currUsername);
        }
    }

    @Override
    public void validateVersion(Long oldVersion, Long currVersion) {
        if (!oldVersion.equals(currVersion)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User version not matched");
        }
    }

    @Override
    public User getEntityById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
    }

    @Override
    public UserResponse getById(String id) {
        User user = getEntityById(id);
        return mappingUserDto(user);
    }

    @Override
    public Page<UserResponse> getAllByPagingAndSearch(PagingRequest pagingRequest, String inquiry) {
        PageRequest pageRequest = PageRequest.of(pagingRequest.getPage(), pagingRequest.getPageSize());
        Page<User> pages = userRepository.findAll(pageRequest);
        List<UserResponse> responses = pages.getContent().stream().map(this::mappingUserDto).collect(Collectors.toList());
        return new PageImpl<>(responses, pageRequest, pages.getTotalElements());
    }

    @Override
    @Transactional
    public void create(CreateUserRequest request) {
        validateBkNotExists(request.getUsername());

        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setStatus(UserStatus.NEED_APPROVAL);
        userRepository.save(user);

        if (!UserRole.ADMIN.equals(request.getRole()) && request.getUserPersonRequest() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please Complete User Profile!");
        } else if (request.getUserPersonRequest() != null) {
            userPersonService.create(request.getUserPersonRequest(), user);
        }
    }

    @Override
    @Transactional
    public void update(UpdateUserRequest request) {
        validateIdExists(request.getId());

        User user = getEntityById(request.getId());

        validateBkNotChange(user.getUsername(), request.getUsername());
        validateVersion(user.getVersion(), request.getVersion());

        user.setUsername(request.getUsername());
        user.setRole(request.getRole());
        userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public void updatePassword(UpdateUserPasswordRequest request) {
        validateIdExists(request.getId());

        User user = getEntityById(request.getId());

        validateVersion(user.getVersion(), request.getVersion());
        validatePassword(user.getPassword(), request.getCurrPassword(), request.getNewPassword(), request.getConfPassword());
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public void delete(String id) {
        User user = getEntityById(id);
        userRepository.delete(user);
    }

    @Override
    public void delete(List<String> ids) {
        for (String id: ids) {
            delete(id);
        }
    }

    @Override
    @Transactional
    public void changeStatus(List<String> ids, UserStatus status) {
        for (String id: ids) {
            validateIdExists(id);
            User user = getEntityById(id);

            if (!user.getStatus().equals(status)) {
                user.setStatus(status);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User status has been " + status);
            }
            userRepository.saveAndFlush(user);
        }
    }

    private void validatePassword(String userPassword, String currPassword, String newPassword, String confPassword) {
        boolean match = passwordEncoder.matches(userPassword, currPassword);

        if (match) {
            String newHashedPassword = passwordEncoder.encode(newPassword);
            String confHashedPassword = passwordEncoder.encode(confPassword);

            if (!passwordEncoder.matches(newHashedPassword, confHashedPassword)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password not matched");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current password not matched");
        }
    }

    private UserResponse mappingUserDto(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setChurchName(eopConfig.getChurch().getName());
        response.setUsername(user.getUsername());
        response.setRole(String.valueOf(user.getRole()));
        response.setStatus(user.getStatus().toString());
        response.setLastLoginInfo(user.getLastLoginInfo());
        response.setIsActive(user.getIsActive());
        response.setVersion(user.getVersion());
        return response;
    }
}
