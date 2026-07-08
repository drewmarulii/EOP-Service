package com.eop.eventservice.service;

import com.eop.baseservice.common.dto.liturgy.request.CreateLiturgySequenceRequest;
import com.eop.baseservice.common.dto.liturgy.request.UpdateLiturgySequenceRequest;
import com.eop.baseservice.common.dto.liturgy.response.LiturgySequenceResponse;
import com.eop.baseservice.common.response.PagingRequest;
import com.eop.eventservice.entity.LiturgyGroup;
import com.eop.eventservice.entity.LiturgySequence;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LiturgySequenceService {

    void validateIdExists(String id);

    void validateVersion(Long oldVersion, Long currVersion);

    LiturgySequence getEntityById(String id);

    List<LiturgySequence> findEntityByLiturgyGroupId(String liturgyGroupId);

    LiturgySequenceResponse getById(String id);

    List<LiturgySequenceResponse> findByLiturgyGroupId(String liturgyGroupId);

    Page<LiturgySequenceResponse> getAllByPagingAndSearch(PagingRequest pagingRequest, String inquiry);

    void create(CreateLiturgySequenceRequest request, LiturgyGroup liturgyGroup);

    void update(UpdateLiturgySequenceRequest request);

    void delete(String id);

    void delete(List<String> ids);

}
