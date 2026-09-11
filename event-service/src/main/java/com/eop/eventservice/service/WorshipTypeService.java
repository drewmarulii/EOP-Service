package com.eop.eventservice.service;

import com.eop.baseservice.common.dto.event.request.CreateWorshipTypeRequest;
import com.eop.baseservice.common.dto.event.request.UpdateWorshipTypeRequest;
import com.eop.baseservice.common.dto.event.response.WorshipTypeResponse;
import com.eop.baseservice.common.response.PagingRequest;
import com.eop.eventservice.entity.WorshipType;
import org.springframework.data.domain.Page;

import java.util.List;

public interface WorshipTypeService {

    void validateIdExists(String id);

    void validateBkNotExists(String code);

    void validateVersion(Long oldVersion, Long currVersion);

    WorshipType getEntityById(String id);

    WorshipTypeResponse getById(String id);

    Page<WorshipTypeResponse> getAllByPagingAndSearch(PagingRequest pagingRequest, String inquiry, Boolean isActive);

    void create(CreateWorshipTypeRequest request);

    void update(UpdateWorshipTypeRequest request);

    void delete(String id);

    void delete(List<String> ids);

}
