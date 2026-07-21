package com.eop.userservice.service;

import com.eop.baseservice.common.dto.user.request.CreateUserPersonRequest;
import com.eop.baseservice.common.dto.user.request.UpdateUserPersonRequest;
import com.eop.baseservice.common.dto.user.response.UserPersonResponse;
import com.eop.baseservice.common.dto.user.response.UserResponse;
import com.eop.baseservice.common.response.PagingRequest;
import com.eop.userservice.entity.User;
import com.eop.userservice.entity.UserPerson;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserPersonService {

    void validateIdExists(String id);

    void validateBkNotExists(String nik);

    void validateVersion(Long oldVersion, Long currVersion);

    UserPerson getEntityById(String id);

    UserPersonResponse getById(String id);

    Page<UserPersonResponse> getAllByPagingAndSearch(PagingRequest pagingRequest, String inquiry);

    void create(CreateUserPersonRequest request, User user);

    void update(UpdateUserPersonRequest request);

    void delete(String id);

    void delete(List<String> ids);

}
