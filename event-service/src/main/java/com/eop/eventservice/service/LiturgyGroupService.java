package com.eop.eventservice.service;

import com.eop.baseservice.common.dto.liturgy.request.CreateLiturgyGroupRequest;
import com.eop.baseservice.common.dto.liturgy.request.UpdateLiturgyGroupRequest;
import com.eop.baseservice.common.dto.liturgy.response.LiturgyGroupResponse;
import com.eop.baseservice.common.response.PagingRequest;
import com.eop.eventservice.entity.LiturgyGroup;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LiturgyGroupService {

    void validateIdExists(String id);

    void validateBkNotExists(String code);

    void validateBkNotChange(String oldCode, String currCode);

    void validateVersion(Long oldVersion, Long currVersion);

    LiturgyGroup getEntityById(String id);

    LiturgyGroupResponse getById(String id);

    Page<LiturgyGroupResponse> getAllByPagingAndSearch(PagingRequest pagingRequest, String inquiry);

    void create(CreateLiturgyGroupRequest request);

    void update(UpdateLiturgyGroupRequest request);

    void delete(String id);

    void delete(List<String> ids);
}
