package com.eop.eventservice.service.impl;

import com.eop.baseservice.common.MyInfoResponse;
import com.eop.baseservice.common.constant.UserRole;
import com.eop.baseservice.common.dto.event.request.CreateEventRequest;
import com.eop.baseservice.common.dto.event.request.UpdateEventRequest;
import com.eop.baseservice.common.dto.event.response.EventParticipantResponse;
import com.eop.baseservice.common.dto.event.response.EventResponse;
import com.eop.baseservice.common.dto.liturgy.response.LiturgyGroupResponse;
import com.eop.baseservice.common.response.PagingRequest;
import com.eop.eventservice.common.constant.EventType;
import com.eop.eventservice.entity.Event;
import com.eop.eventservice.entity.LiturgyGroup;
import com.eop.eventservice.feign.UserServiceClient;
import com.eop.eventservice.repository.EventRepository;
import com.eop.eventservice.service.EventService;
import com.eop.eventservice.service.LiturgyGroupService;
import com.eop.eventservice.service.PosterGeneratorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final PosterGeneratorService posterGeneratorService;
    private final LiturgyGroupService liturgyGroupService;
    private final UserServiceClient userServiceClient;

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
        PageRequest pageRequest = PageRequest.of(pagingRequest.getPage(), pagingRequest.getPageSize());
        Page<Event> pages = eventRepository.findAll(pageRequest);
        List<EventResponse> responses = pages.getContent().stream().map(this::mappingEventDto).collect(Collectors.toList());
        return new PageImpl<>(responses, pageRequest, pages.getTotalElements());
    }

    @Override
    public List<EventParticipantResponse> getAllEventParticipant(String inquiry, String eventId) {
        Optional<Event> event = eventRepository.findById(eventId);

        if (event.isPresent()) {
            return event.get().getParticipantData().entrySet().stream()
                    .map(e -> mappingEventParticipantDto(e.getKey(), e.getValue()))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional
    public void create(CreateEventRequest request) {
        MyInfoResponse myInfo = getMyInfo();
        if (!UserRole.ADMIN.toString().equalsIgnoreCase(myInfo.getUserRole())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event can't be created. Contact Admin!");
        }

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
        event.setCreatedBy(myInfo.getUid());
        event.setUpdatedBy(myInfo.getUid());
        eventRepository.save(event);
    }

    @Override
    @Transactional
    public void update(UpdateEventRequest request) {
        MyInfoResponse myInfo = getMyInfo();
        if (!UserRole.ADMIN.toString().equalsIgnoreCase(myInfo.getUserRole())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event can't be created. Contact Admin!");
        }

        validateIdExists(request.getId());
        Event event = getEntityById(request.getId());

        LiturgyGroup liturgyGroup = liturgyGroupService.getEntityById(request.getLiturgyGroupId());
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
        event.setUpdatedBy(myInfo.getUid());

        eventRepository.saveAndFlush(event);
    }

    @Override
    @Transactional
    public void delete(String id) {
        Event event = getEntityById(id);
        eventRepository.delete(event);
    }

    @Override
    public void delete(List<String> ids) {
        for (String id: ids) {
            delete(id);
        }
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
        BeanUtils.copyProperties(event, response);

        LiturgyGroup liturgyGroup = event.getLiturgyGroup();
        response.setLiturgyGroupId(liturgyGroup.getId());
        response.setLiturgyGroupName(liturgyGroup.getName());
        response.setEventType(event.getEventType().toString());
        return response;
    }

    private EventParticipantResponse mappingEventParticipantDto(String key, Object value) {
        EventParticipantResponse response = new EventParticipantResponse();
        response.setParticipantName(key);
        response.setParticipantName(value.toString());
        return response;
    }

    private MyInfoResponse getMyInfo() {
        return userServiceClient.myInfo();
    }
}
