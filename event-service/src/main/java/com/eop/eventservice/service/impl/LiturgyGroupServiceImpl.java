package com.eop.eventservice.service.impl;

import com.eop.baseservice.common.MyInfoResponse;
import com.eop.baseservice.common.constant.UserRole;
import com.eop.baseservice.common.dto.liturgy.request.CreateLiturgyGroupRequest;
import com.eop.baseservice.common.dto.liturgy.request.CreateLiturgySequenceRequest;
import com.eop.baseservice.common.dto.liturgy.request.UpdateLiturgyGroupRequest;
import com.eop.baseservice.common.dto.liturgy.response.LiturgyGroupResponse;
import com.eop.baseservice.common.response.PagingRequest;
import com.eop.eventservice.entity.LiturgyGroup;
import com.eop.eventservice.feign.UserServiceClient;
import com.eop.eventservice.repository.LiturgyGroupRepository;
import com.eop.eventservice.service.LiturgyGroupService;
import com.eop.eventservice.service.LiturgySequenceService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class LiturgyGroupServiceImpl implements LiturgyGroupService {

    private final LiturgyGroupRepository liturgyGroupRepository;
    private final LiturgySequenceService liturgySequenceService;
    private final UserServiceClient userServiceClient;

    @Override
    public void validateIdExists(String id) {
        if (!liturgyGroupRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Liturgy Group not found");
        }
    }

    @Override
    public void validateBkNotExists(String code) {
        if (liturgyGroupRepository.existsByCode(code)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Code found! please use other code");
        }
    }

    @Override
    public void validateBkNotChange(String oldCode, String currCode) {
        if (!oldCode.equalsIgnoreCase(currCode)) {
            validateBkNotExists(currCode);
        }
    }

    @Override
    public void validateVersion(Long oldVersion, Long currVersion) {
        if (!oldVersion.equals(currVersion)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Liturgy Group version not matched");
        }
    }

    @Override
    public LiturgyGroup getEntityById(String id) {
        return liturgyGroupRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Liturgy Group not found"));
    }

    @Override
    public LiturgyGroupResponse getById(String id) {
        LiturgyGroup liturgyGroup = getEntityById(id);
        return mappingLiturgyGroupDto(liturgyGroup);
    }

    @Override
    public Page<LiturgyGroupResponse> getAllByPagingAndSearch(PagingRequest pagingRequest, String inquiry) {
        PageRequest pageRequest = PageRequest.of(pagingRequest.getPage(), pagingRequest.getPageSize());
        Page<LiturgyGroup> pages = liturgyGroupRepository.findAll(pageRequest);
        List<LiturgyGroupResponse> responses = pages.getContent().stream().map(this::mappingLiturgyGroupDto).collect(Collectors.toList());
        return new PageImpl<>(responses, pageRequest, pages.getTotalElements());
    }

    @Override
    @Transactional
    public void create(CreateLiturgyGroupRequest request) {
        MyInfoResponse myInfo = getMyInfo();
        if (!UserRole.ADMIN.toString().equalsIgnoreCase(myInfo.getUserRole())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Liturgy Group can't be created. Contact Admin!");
        }

        validateBkNotExists(request.getCode());

        LiturgyGroup liturgyGroup = new LiturgyGroup();
        liturgyGroup.setCode(request.getCode());
        liturgyGroup.setName(request.getName());
        liturgyGroup.setDescription(request.getDescription());
        liturgyGroup.setCreatedBy(myInfo.getUid());
        liturgyGroup.setUpdatedBy(myInfo.getUid());
        liturgyGroupRepository.save(liturgyGroup);

        for (CreateLiturgySequenceRequest sequenceRequest : request.getLiturgySequenceRequests()) {
            liturgySequenceService.create(sequenceRequest, liturgyGroup);
        }
    }

    @Override
    @Transactional
    public void update(UpdateLiturgyGroupRequest request) {
        MyInfoResponse myInfo = getMyInfo();
        if (!UserRole.ADMIN.toString().equalsIgnoreCase(myInfo.getUserRole())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Liturgy Group can't be created. Contact Admin!");
        }

        validateIdExists(request.getId());

        LiturgyGroup liturgyGroup = getEntityById(request.getId());
        validateBkNotChange(liturgyGroup.getCode(), request.getCode());
        validateVersion(liturgyGroup.getVersion(), request.getVersion());

        liturgyGroup.setCode(request.getCode());
        liturgyGroup.setName(request.getName());
        liturgyGroup.setDescription(request.getDescription());
        liturgyGroup.setUpdatedBy(myInfo.getUid());
        liturgyGroupRepository.saveAndFlush(liturgyGroup);
    }

    @Override
    @Transactional
    public void delete(String id) {
        LiturgyGroup liturgyGroup = getEntityById(id);
        liturgyGroupRepository.delete(liturgyGroup);
    }

    @Override
    public void delete(List<String> ids) {
        for (String id: ids) {
            delete(id);
        }
    }

    private LiturgyGroupResponse mappingLiturgyGroupDto(LiturgyGroup liturgyGroup) {
        LiturgyGroupResponse response = new LiturgyGroupResponse();
        response.setId(liturgyGroup.getId());
        response.setCode(liturgyGroup.getCode());
        response.setName(liturgyGroup.getName());
        response.setVersion(liturgyGroup.getVersion());
        return response;
    }

    private MyInfoResponse getMyInfo() {
        return userServiceClient.myInfo();
    }
}
