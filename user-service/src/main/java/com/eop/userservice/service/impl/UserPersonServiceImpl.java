package com.eop.userservice.service.impl;

import com.eop.baseservice.common.dto.user.request.CreateUserPersonRequest;
import com.eop.baseservice.common.dto.user.request.UpdateUserPersonRequest;
import com.eop.baseservice.common.dto.user.response.UserPersonResponse;
import com.eop.baseservice.common.dto.user.response.UserResponse;
import com.eop.baseservice.common.response.PagingRequest;
import com.eop.userservice.entity.User;
import com.eop.userservice.entity.UserPerson;
import com.eop.userservice.repository.UserPersonRepository;
import com.eop.userservice.service.UserPersonService;
import com.eop.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserPersonServiceImpl implements UserPersonService {

    private final UserService userService;
    private final UserPersonRepository userPersonRepository;

    @Override
    public void validateIdExists(String id) {
        if (!userPersonRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Profile not found");
        }
    }

    @Override
    public void validateBkNotExists(String nik) {
        if (!userPersonRepository.existsByNik(nik)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Profile Can't be Created, Data Invalid");
        }
    }

    @Override
    public void validateVersion(Long oldVersion, Long currVersion) {
        if (!oldVersion.equals(currVersion)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Profile version not matched");
        }
    }

    @Override
    public UserPerson getEntityById(String id) {
        return userPersonRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Profile not found"));
    }

    @Override
    public UserPersonResponse getById(String id) {
        UserPerson userPerson = getEntityById(id);
        return mappingUserPersonDto(userPerson);
    }

    @Override
    public Page<UserPersonResponse> getAllByPagingAndSearch(PagingRequest pagingRequest, String inquiry) {
        PageRequest pageRequest = PageRequest.of(pagingRequest.getPage(), pagingRequest.getPageSize());
        Page<UserPerson> pages = userPersonRepository.findAll(pageRequest);
        List<UserPersonResponse> responses = pages.getContent().stream().map(this::mappingUserPersonDto).collect(Collectors.toList());
        return new PageImpl<>(responses, pageRequest, pages.getTotalElements());
    }

    @Override
    @Transactional
    public void create(CreateUserPersonRequest request, User user) {
        validateBkNotExists(request.getNik());

        UserPerson userPerson = new UserPerson();
        BeanUtils.copyProperties(request, userPerson);
        userPerson.setUser(user);

        if (StringUtils.isNotBlank(request.getParentId())) {
            User parent = userService.getEntityById(request.getParentId());
            userPerson.setParent(parent);
        }

        userPersonRepository.save(userPerson);
    }

    @Override
    @Transactional
    public void update(UpdateUserPersonRequest request) {
        validateIdExists(request.getId());

        UserPerson userPerson = getEntityById(request.getId());
        if (!userPerson.getNik().equalsIgnoreCase(request.getNik())) {
            validateBkNotExists(request.getNik());
        }

        BeanUtils.copyProperties(request, userPerson);
        validateVersion(userPerson.getVersion(), request.getVersion());

        if (StringUtils.isNotBlank(request.getParentId())) {
            User parent = userService.getEntityById(request.getParentId());
            userPerson.setParent(parent);
        }
    }

    @Override
    @Transactional
    public void delete(String id) {
        UserPerson userPerson = getEntityById(id);
        userPersonRepository.delete(userPerson);
    }

    @Override
    @Transactional
    public void delete(List<String> ids) {
        for (String id: ids) {
            delete(id);
        }
    }

    private UserPersonResponse mappingUserPersonDto(UserPerson userPerson) {
        UserPersonResponse response = new UserPersonResponse();
        BeanUtils.copyProperties(userPerson, response);

        User parent = userPerson.getParent();
        response.setParentId(parent.getId());
        response.setParentUid(parent.getUid());

        UserPerson parentProfile = userPersonRepository.findByParentId(parent.getId());
        response.setParentProfileId(parentProfile.getId());
        response.setParentFullName(parentProfile.getFullName());
        return response;
    }
}
