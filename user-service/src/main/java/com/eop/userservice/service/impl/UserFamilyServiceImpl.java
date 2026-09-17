package com.eop.userservice.service.impl;

import com.eop.baseservice.common.dto.user.request.CreateUserFamilyRelationRequest;
import com.eop.baseservice.common.dto.user.request.CreateUserFamilyRequest;
import com.eop.baseservice.common.dto.user.request.UpdateUserFamilyRequest;
import com.eop.baseservice.common.dto.user.response.UserFamilyMemberResponse;
import com.eop.baseservice.common.dto.user.response.UserFamilyResponse;
import com.eop.baseservice.service.ValidationService;
import com.eop.userservice.entity.UserFamily;
import com.eop.userservice.entity.UserPerson;
import com.eop.userservice.repository.UserFamilyRepository;
import com.eop.userservice.service.UserFamilyService;
import com.eop.userservice.service.UserPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserFamilyServiceImpl extends ValidationService<UserFamily, String> implements UserFamilyService {

    private final UserFamilyRepository userFamilyRepository;
    private final UserPersonService userPersonService;

    @Override
    protected JpaRepository<UserFamily, String> getRepository() {
        return userFamilyRepository;
    }

    @Override
    public void validateBkNotExists(String leaderId, String memberId) {
        if (userFamilyRepository.existsByLeaderUserIdAndMemberUserId(leaderId, memberId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't assign leader and member to the family group");
        }
    }

    @Override
    public UserFamily getEntityById(String id) {
        return userFamilyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Family not found"));
    }

    @Override
    public UserFamilyResponse getUserFamilyByUserPersonId(String userPersonId) {
        UserPerson userPerson = userPersonService.getEntityById(userPersonId);
        String leaderUserId = userPerson.getId();

        if (!userPerson.getIsFamilyLeader()) {
            UserFamily userFamily = userFamilyRepository.findByMemberUserId(userPersonId);
            leaderUserId = userFamily.getLeaderUser().getId();
        }

        UserFamilyResponse response = new UserFamilyResponse();
        response.setUserFamilyLeader(userPersonService.getById(leaderUserId));

        List<UserFamily> userFamilyMembers = userFamilyRepository.findByLeaderUserId(leaderUserId);
        List<UserFamilyMemberResponse> familyMemberResponses = new ArrayList<>();
        for (UserFamily familyMember : userFamilyMembers) {
            UserFamilyMemberResponse memberResponse = new UserFamilyMemberResponse();
            BeanUtils.copyProperties(familyMember, memberResponse);
            memberResponse.setUserFamilyMember(userPersonService.getById(familyMember.getMemberUser().getId()));
            memberResponse.setRelationship(familyMember.getRelationship().toString());
            familyMemberResponses.add(memberResponse);
        }
        response.setUserFamilyMemberResponses(familyMemberResponses);

        return response;
    }

    @Override
    @Transactional
    public void create(CreateUserFamilyRequest request) {
        UserPerson familyLeader = userPersonService.getEntityById(request.getLeaderUserId());

        if (!familyLeader.getIsFamilyLeader()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not the family leader");
        }

        for (CreateUserFamilyRelationRequest familyMemberReq : request.getUserFamilyRelationRequests()) {
            validateBkNotExists(familyLeader.getId(), familyMemberReq.getMemberUserId());
            UserFamily userFamily = new UserFamily();
            UserPerson familyMember = userPersonService.getEntityById(familyMemberReq.getMemberUserId());
            userFamily.setLeaderUser(familyLeader);
            userFamily.setMemberUser(familyMember);
            userFamily.setRelationship(familyMemberReq.getRelationship());
            userFamilyRepository.save(userFamily);
        }
    }

    @Override
    @Transactional
    public void update(UpdateUserFamilyRequest request) {
        validateIdExists(request.getId());
        UserFamily userFamily = getEntityById(request.getId());
        validateVersion(userFamily.getVersion(), request.getVersion());
        userFamily.setRelationship(request.getRelationship());
        userFamilyRepository.saveAndFlush(userFamily);
    }

    @Override
    @Transactional
    public void delete(String id) {
        UserFamily userFamily = getEntityById(id);
        userFamilyRepository.delete(userFamily);
    }

    @Override
    public void delete(List<String> ids) {
        for (String id: ids) {
            delete(id);
        }
    }
}
