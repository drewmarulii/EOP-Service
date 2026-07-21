package com.eop.eventservice.service.impl;

import com.eop.baseservice.common.MyInfoResponse;
import com.eop.baseservice.common.constant.UserRole;
import com.eop.baseservice.common.dto.liturgy.request.CreateLiturgySequenceRequest;
import com.eop.baseservice.common.dto.liturgy.request.UpdateLiturgySequenceRequest;
import com.eop.baseservice.common.dto.liturgy.response.LiturgySequenceResponse;
import com.eop.baseservice.common.response.PagingRequest;
import com.eop.eventservice.entity.LiturgyGroup;
import com.eop.eventservice.entity.LiturgySequence;
import com.eop.eventservice.feign.UserServiceClient;
import com.eop.eventservice.repository.LiturgySequenceRepository;
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
public class LiturgySequenceServiceImpl implements LiturgySequenceService {

    private final LiturgySequenceRepository liturgySequenceRepository;
    private final UserServiceClient userServiceClient;

    @Override
    public void validateIdExists(String id) {
        if (!liturgySequenceRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Liturgy Sequence not found");
        }
    }

    @Override
    public void validateVersion(Long oldVersion, Long currVersion) {
        if (!oldVersion.equals(currVersion)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Liturgy Sequence version not matched");
        }
    }

    @Override
    public LiturgySequence getEntityById(String id) {
        return liturgySequenceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Liturgy Sequence not found"));
    }

    @Override
    public List<LiturgySequence> findEntityByLiturgyGroupId(String liturgyGroupId) {
        return liturgySequenceRepository.findAllByLiturgyGroupId(liturgyGroupId);
    }

    @Override
    public LiturgySequenceResponse getById(String id) {
        LiturgySequence liturgySequence = getEntityById(id);
        return mappingLiturgySequenceDto(liturgySequence);
    }

    @Override
    public List<LiturgySequenceResponse> findByLiturgyGroupId(String liturgyGroupId) {
        return findEntityByLiturgyGroupId(liturgyGroupId)
                .stream().map(this::mappingLiturgySequenceDto).collect(Collectors.toList());
    }

    @Override
    public Page<LiturgySequenceResponse> getAllByPagingAndSearch(PagingRequest pagingRequest, String inquiry) {
        PageRequest pageRequest = PageRequest.of(pagingRequest.getPage(), pagingRequest.getPageSize());
        Page<LiturgySequence> pages = liturgySequenceRepository.findAll(pageRequest);
        List<LiturgySequenceResponse> responses = pages.getContent().stream().map(this::mappingLiturgySequenceDto).collect(Collectors.toList());
        return new PageImpl<>(responses, pageRequest, pages.getTotalElements());
    }

    @Override
    @Transactional
    public void create(CreateLiturgySequenceRequest request, LiturgyGroup liturgyGroup) {
        MyInfoResponse myInfo = getMyInfo();
        if (!UserRole.ADMIN.toString().equalsIgnoreCase(myInfo.getUserRole())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Liturgy Group can't be created. Contact Admin!");
        }

        LiturgySequence liturgySequence = new LiturgySequence();
        liturgySequence.setSequenceNumber(request.getSequenceNumber());
        liturgySequence.setTitle(request.getTitle());
        liturgySequence.setDescription(request.getDescription());
        liturgySequence.setLiturgyGroup(liturgyGroup);
        liturgySequence.setCreatedBy(myInfo.getUid());
        liturgySequence.setUpdatedBy(myInfo.getUid());
        liturgySequenceRepository.save(liturgySequence);
    }

    @Override
    @Transactional
    public void update(UpdateLiturgySequenceRequest request) {
        MyInfoResponse myInfo = getMyInfo();
        if (!UserRole.ADMIN.toString().equalsIgnoreCase(myInfo.getUserRole())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Liturgy Group can't be created. Contact Admin!");
        }

        validateIdExists(request.getId());
        LiturgySequence liturgySequence = getEntityById(request.getId());
        liturgySequence.setSequenceNumber(request.getSequenceNumber());
        liturgySequence.setTitle(request.getTitle());
        liturgySequence.setDescription(request.getDescription());
        liturgySequence.setUpdatedBy(myInfo.getUid());
        liturgySequenceRepository.saveAndFlush(liturgySequence);
    }

    @Override
    @Transactional
    public void delete(String id) {
        LiturgySequence liturgySequence = getEntityById(id);
        liturgySequenceRepository.delete(liturgySequence);
    }

    @Override
    public void delete(List<String> ids) {
        for (String id: ids) {
            delete(id);
        }
    }

    private LiturgySequenceResponse mappingLiturgySequenceDto(LiturgySequence liturgySequence) {
        LiturgySequenceResponse response = new LiturgySequenceResponse();
        response.setId(liturgySequence.getId());
        response.setSequenceNumber(liturgySequence.getSequenceNumber());
        response.setTitle(liturgySequence.getTitle());
        response.setDescription(liturgySequence.getDescription());
        response.setVersion(liturgySequence.getVersion());
        return response;
    }

    private MyInfoResponse getMyInfo() {
        return userServiceClient.myInfo();
    }
}
