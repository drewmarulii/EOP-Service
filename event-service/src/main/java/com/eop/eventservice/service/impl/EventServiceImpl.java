package com.eop.eventservice.service.impl;

import com.eop.baseservice.common.dto.event.request.CreateEventRequest;
import com.eop.baseservice.common.dto.event.request.UpdateEventRequest;
import com.eop.baseservice.common.dto.event.response.EventResponse;
import com.eop.baseservice.common.response.PagingRequest;
import com.eop.eventservice.common.constant.EventType;
import com.eop.eventservice.entity.Event;
import com.eop.eventservice.entity.LiturgyGroup;
import com.eop.eventservice.repository.EventRepository;
import com.eop.eventservice.service.EventService;
import com.eop.eventservice.service.LiturgyGroupService;
import com.eop.eventservice.service.PosterGeneratorService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final PosterGeneratorService posterGeneratorService;
    private final LiturgyGroupService liturgyGroupService;

    @Override
    public void validateIdExists(String id) {
        if (!eventRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event not found");
        }
    }

    @Override
    public void validateVersion(Long oldVersion, Long currVersion) {
        if (!oldVersion.equals(currVersion)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event version not matched");
        }
    }

    @Override
    public Event getEntityById(String id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event not found"));
    }

    @Override
    public EventResponse getById(String id) {
        return null;
    }

    @Override
    public Page<EventResponse> getAllByPagingAndSearch(PagingRequest pagingRequest, String inquiry) {
        return null;
    }

    @Override
    public void create(CreateEventRequest request) {
        LiturgyGroup liturgyGroup = liturgyGroupService.getEntityById(request.getLiturgyGroupId());

        Event event = new Event();
        event.setLiturgyGroup(liturgyGroup);
        event.setTriwulan(request.getTriwulan());
        event.setYear(request.getYear());
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setEventStartTime(LocalDateTime.parse(request.getEventStartTime()));

        if (Objects.nonNull(request.getEventEndTime())) {
            event.setEventEndTime(LocalDateTime.parse(request.getEventEndTime()));
        }

        event.setEventType(EventType.valueOf(request.getEventType()));

        Map<String, Object> participantData = new LinkedHashMap<>();

        request.getParticipantData()
            .entrySet()
            .forEach(entry -> {
                String title = entry.getKey();
                Object participant = entry.getValue();
                participantData.put(title, participant);
            });

        event.setParticipantData(participantData);
        eventRepository.save(event);
    }

    @Override
    public void update(UpdateEventRequest request) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void delete(List<String> ids) {

    }

    @Override
    public String generatePoster(String id) {
        Event event = getEntityById(id);
        try {
            return posterGeneratorService.generate(event);
        } catch (Exception e) {
            throw new RuntimeException("Generate PDF failed", e);
        }
    }

    @Override
    public List<String> generatePosters(List<String> ids) {
        List<String> posters = new ArrayList<>();
        for (String id : ids) {
            posters.add(generatePoster(id));
        }
        return posters;
    }

    private EventResponse mappingEventDto(Event event) {
        EventResponse response = new EventResponse();
        return response;
    }
}
