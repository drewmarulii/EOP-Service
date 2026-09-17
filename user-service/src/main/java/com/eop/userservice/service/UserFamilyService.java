package com.eop.userservice.service;

import com.eop.baseservice.common.dto.user.request.CreateUserFamilyRequest;
import com.eop.baseservice.common.dto.user.request.UpdateUserFamilyRequest;
import com.eop.baseservice.common.dto.user.response.UserFamilyResponse;
import com.eop.userservice.entity.UserFamily;

import java.util.List;

public interface UserFamilyService {

    void validateBkNotExists(String leaderId, String memberId);

    UserFamily getEntityById(String id);

    UserFamilyResponse getUserFamilyByUserPersonId(String userPersonId);

    void create(CreateUserFamilyRequest request);

    void update(UpdateUserFamilyRequest request);

    void delete(String id);

    void delete(List<String> ids);

}
