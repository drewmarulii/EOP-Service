package com.eop.eventservice.service;

import com.eop.baseservice.common.dto.event.request.CreateScheduleWorshipRequest;
import com.eop.baseservice.common.dto.event.request.UpdateScheduleWorshipRequest;
import com.eop.baseservice.common.dto.event.response.ScheduleWorshipResponse;
import com.eop.baseservice.common.response.PagingRequest;
import com.eop.eventservice.entity.ScheduleWorship;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ScheduleWorshipService {

    void validateBkNotExists(String worshipType, String startTime, String endTime);

    ScheduleWorshipResponse getById(String id);

    ScheduleWorship getEntityById(String id);

    Page<ScheduleWorshipResponse> getAllByPagingAndSearch(PagingRequest pagingRequest, String inquiry, Boolean isActive);

    void create(CreateScheduleWorshipRequest request);

    void update(UpdateScheduleWorshipRequest request);

    void delete(String id);

    void delete(List<String> ids);

}
