package com.eop.userservice.service;

import com.eop.baseservice.common.constant.UserStatus;
import com.eop.baseservice.common.dto.user.request.CreateUserRequest;
import com.eop.baseservice.common.dto.user.request.UpdateUserPasswordRequest;
import com.eop.baseservice.common.dto.user.request.UpdateUserRequest;
import com.eop.baseservice.common.response.PagingRequest;
import com.eop.baseservice.common.dto.user.response.UserResponse;
import com.eop.userservice.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    void validateIdExists(String id);

    void validateBkNotExists(String username);

    void validateBkNotChange(String oldUsername, String currUsername);

    void validateVersion(Long oldVersion, Long currVersion);

    User getEntityById(String id);

    UserResponse getById(String id);

    Page<UserResponse> getAllByPagingAndSearch(PagingRequest pagingRequest, String inquiry);

    void create(CreateUserRequest request);

    void update(UpdateUserRequest request);

    void updatePassword(UpdateUserPasswordRequest request);

    void delete(String id);

    void delete(List<String> ids);

    void changeStatus(List<String> ids, UserStatus status);

}