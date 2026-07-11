package com.eop.eventservice.service;

import com.eop.baseservice.common.dto.event.request.CreateEventRequest;
import com.eop.baseservice.common.dto.event.request.UpdateEventRequest;
import com.eop.baseservice.common.dto.event.response.EventParticipantResponse;
import com.eop.baseservice.common.dto.event.response.EventResponse;
import com.eop.baseservice.common.response.PagingRequest;
import com.eop.eventservice.entity.Event;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EventService {

    void validateIdExists(String id);

    void validateVersion(Long oldVersion, Long currVersion);

    Event getEntityById(String id);

    EventResponse getById(String id);

    Page<EventResponse> getAllByPagingAndSearch(PagingRequest pagingRequest, String inquiry);

    List<EventParticipantResponse> getAllEventParticipant(String inquiry, String eventId);

    void create(CreateEventRequest request);

    void update(UpdateEventRequest request);

    void delete(String id);

    void delete(List<String> ids);

    String generatePoster(String id);

    List<String> generatePosters(List<String> ids);

}
