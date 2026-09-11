package com.eop.eventservice.service.impl;

import com.eop.baseservice.common.MyInfoResponse;
import com.eop.baseservice.common.constant.UserRole;
import com.eop.baseservice.common.dto.event.request.CreateWorshipTypeRequest;
import com.eop.baseservice.common.dto.event.request.UpdateWorshipTypeRequest;
import com.eop.baseservice.common.dto.event.response.WorshipTypeResponse;
import com.eop.baseservice.common.response.PagingRequest;
import com.eop.eventservice.entity.WorshipType;
import com.eop.eventservice.feign.UserServiceClient;
import com.eop.eventservice.repository.WorshipTypeRepository;
import com.eop.eventservice.service.WorshipTypeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WorshipTypeServiceImpl implements WorshipTypeService {

    private final WorshipTypeRepository worshipTypeRepository;
//    private final PosterGeneratorService posterGeneratorService;
    private final UserServiceClient userServiceClient;

    @Override
    public void validateIdExists(String id) {
        if (!worshipTypeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Worship Type not found");
        }
    }

    @Override
    public void validateBkNotExists(String code) {
        if (worshipTypeRepository.existsByCode(code)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Worship Type found! please use other");
        }
    }

    @Override
    public void validateVersion(Long oldVersion, Long currVersion) {
        if (!oldVersion.equals(currVersion)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Worship Type version not matched");
        }
    }

    @Override
    public WorshipType getEntityById(String id) {
        return worshipTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Worship Type not found"));
    }

    @Override
    public WorshipTypeResponse getById(String id) {
        return null;
    }

    @Override
    public Page<WorshipTypeResponse> getAllByPagingAndSearch(PagingRequest pagingRequest, String inquiry, Boolean isActive) {
        PageRequest pageRequest = PageRequest.of(pagingRequest.getPage(), pagingRequest.getPageSize());
        Page<WorshipType> pages = worshipTypeRepository.findAll(pageRequest);
        List<WorshipTypeResponse> responses = pages.getContent().stream().map(this::mappingToDto).collect(Collectors.toList());
        return new PageImpl<>(responses, pageRequest, pages.getTotalElements());
    }

    @Override
    @Transactional
    public void create(CreateWorshipTypeRequest request) {
        MyInfoResponse myInfo = getMyInfo();
        if (!UserRole.ADMIN.toString().equalsIgnoreCase(myInfo.getUserRole())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Worship Type can't be created. Contact Admin!");
        }
        validateBkNotExists(request.getCode());

        WorshipType worshipType = new WorshipType();
        BeanUtils.copyProperties(request, worshipType);
        worshipType.setWorship(request.getWorship());

        Map<String, String> liturgyTemplate = new HashMap<>();
        for (String liturgy : request.getLiturgyTemplate()) {
            liturgyTemplate.put(liturgy, "");
        }
        worshipType.setLiturgyTemplate(liturgyTemplate);
        worshipTypeRepository.save(worshipType);
    }

    @Override
    @Transactional
    public void update(UpdateWorshipTypeRequest request) {
        MyInfoResponse myInfo = getMyInfo();
        if (!UserRole.ADMIN.toString().equalsIgnoreCase(myInfo.getUserRole())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Worship Type can't be created. Contact Admin!");
        }

        validateIdExists(request.getId());
        WorshipType worshipType = getEntityById(request.getId());
        BeanUtils.copyProperties(request, worshipType);
        worshipType.setWorship(request.getWorship());

        Map<String, String> liturgyTemplate = new HashMap<>();
        for (String liturgy : request.getLiturgyTemplate()) {
            liturgyTemplate.put(liturgy, "");
        }
        worshipType.setLiturgyTemplate(liturgyTemplate);
        worshipTypeRepository.saveAndFlush(worshipType);
    }

    @Override
    @Transactional
    public void delete(String id) {
        WorshipType worshipType = getEntityById(id);
        worshipTypeRepository.delete(worshipType);
    }

    @Override
    public void delete(List<String> ids) {
        for (String id: ids) {
            delete(id);
        }
    }

    private WorshipTypeResponse mappingToDto(WorshipType worshipType) {
        WorshipTypeResponse response = new WorshipTypeResponse();
        BeanUtils.copyProperties(worshipType, response);
        return response;
    }

    private MyInfoResponse getMyInfo() {
        return userServiceClient.myInfo();
    }
}
