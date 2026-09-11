package com.eop.userservice.service.impl;

import com.eop.baseservice.common.constant.Gender;
import com.eop.baseservice.common.constant.MaritalStatus;
import com.eop.baseservice.common.dto.user.request.CreateUserPersonRequest;
import com.eop.baseservice.common.dto.user.request.UpdateUserPersonRequest;
import com.eop.baseservice.common.dto.user.response.UserPersonResponse;
import com.eop.baseservice.common.dto.user.response.UserResponse;
import com.eop.baseservice.common.response.PagingRequest;
import com.eop.baseservice.helper.SpecificationHelper;
import com.eop.userservice.entity.User;
import com.eop.userservice.entity.UserPerson;
import com.eop.userservice.repository.UserPersonRepository;
import com.eop.userservice.service.UserPersonService;
import com.eop.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserPersonServiceImpl implements UserPersonService {

    @Autowired
    @Lazy
    private UserService userService;
    private final UserPersonRepository userPersonRepository;

    @Override
    public void validateIdExists(String id) {
        if (!userPersonRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Profile not found");
        }
    }

    @Override
    public void validateBkNotExists(String userId) {
        if (userPersonRepository.existsByUserId(userId)) {
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
        PageRequest pageRequest = PageRequest.of(pagingRequest.getPage(), pagingRequest.getPageSize(),
                SpecificationHelper.createSort(UserPerson.class, pagingRequest.getSortBy()));
        Specification<UserPerson> spec = SpecificationHelper.filter(inquiry, null, "nik", "fullName", "address", "mobilePhone", "email");
        Page<UserPerson> pages = userPersonRepository.findAll(spec, pageRequest);
        List<UserPersonResponse> responses = pages.getContent().stream().map(this::mappingUserPersonDto).collect(Collectors.toList());
        return new PageImpl<>(responses, pageRequest, pages.getTotalElements());
    }

    @Override
    @Transactional
    public void create(CreateUserPersonRequest request, User user) {
        validateBkNotExists(user.getId());

        UserPerson userPerson = new UserPerson();
        BeanUtils.copyProperties(request, userPerson);
        userPerson.setUser(user);
        userPerson.setGender(request.getGender());
        userPerson.setMaritalStatus(request.getMaritalStatus());

        if (MaritalStatus.MARRIED.equals(request.getMaritalStatus())) {
            if (request.getMarriedDate() != null) {
                userPerson.setMarriedDate(request.getMarriedDate());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please Insert Married Date");
            }
        }

        if (Boolean.TRUE.equals(request.getIsPassedAway())) {
            if (request.getPassedAwayDate() != null) {
                userPerson.setPassedAwayDate(request.getPassedAwayDate());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please Insert Passed Away Date");
            }
        }

        userPersonRepository.save(userPerson);
    }

    @Override
    @Transactional
    public void update(UpdateUserPersonRequest request) {
        validateIdExists(request.getId());

        UserPerson userPerson = getEntityById(request.getId());
        validateVersion(userPerson.getVersion(), request.getVersion());

        BeanUtils.copyProperties(request, userPerson);

        User user = userService.getEntityById(request.getUserId());
        userPerson.setUser(user);
        userPerson.setGender(request.getGender());
        userPerson.setMaritalStatus(request.getMaritalStatus());

        if (MaritalStatus.MARRIED.equals(request.getMaritalStatus())) {
            if (request.getMarriedDate() != null) {
                userPerson.setMarriedDate(request.getMarriedDate());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please Insert Married Date");
            }
        }

        if (Boolean.TRUE.equals(request.getIsPassedAway())) {
            if (request.getPassedAwayDate() != null) {
                userPerson.setPassedAwayDate(request.getPassedAwayDate());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please Insert Passed Away Date");
            }
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
        response.setMaritalStatus(userPerson.getMaritalStatus());
        response.setGender(userPerson.getGender());

        UserResponse userResponse = userService.getById(userPerson.getUser().getId());
        response.setUserResponse(userResponse);

        return response;
    }
}
