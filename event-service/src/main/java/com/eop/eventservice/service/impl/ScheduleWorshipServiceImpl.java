package com.eop.eventservice.service.impl;

import com.eop.baseservice.common.MyInfoResponse;
import com.eop.baseservice.common.constant.UserRole;
import com.eop.baseservice.common.dto.event.request.CreateScheduleWorshipRequest;
import com.eop.baseservice.common.dto.event.request.UpdateScheduleWorshipRequest;
import com.eop.baseservice.common.dto.event.response.ScheduleWorshipResponse;
import com.eop.baseservice.common.dto.event.response.WorshipTypeResponse;
import com.eop.baseservice.common.response.PagingRequest;
import com.eop.baseservice.service.ValidationService;
import com.eop.eventservice.entity.ScheduleWorship;
import com.eop.eventservice.entity.WorshipType;
import com.eop.eventservice.feign.UserServiceClient;
import com.eop.eventservice.repository.ScheduleWorshipRepository;
import com.eop.eventservice.service.ScheduleWorshipService;
import com.eop.eventservice.service.WorshipTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleWorshipServiceImpl extends ValidationService<ScheduleWorship, String> implements ScheduleWorshipService {

    private final ScheduleWorshipRepository scheduleWorshipRepository;
    private final WorshipTypeService worshipTypeService;
    private final UserServiceClient userServiceClient;

    @Override
    protected JpaRepository<ScheduleWorship, String> getRepository() {
        return scheduleWorshipRepository;
    }

    @Override
    public void validateBkNotExists(String worshipTypeId, String startTime, String endTime) {
        if (scheduleWorshipRepository.existsByWorshipTypeIdAndStartTimeAndEndTime(worshipTypeId, startTime, endTime)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Schedule Worship found!");
        }
    }

    @Override
    public ScheduleWorshipResponse getById(String id) {
        return mappingToDto(getEntityById(id));
    }

    @Override
    public ScheduleWorship getEntityById(String id) {
        return scheduleWorshipRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found"));
    }

    @Override
    public Page<ScheduleWorshipResponse> getAllByPagingAndSearch(PagingRequest pagingRequest, String inquiry, Boolean isActive) {
        PageRequest pageRequest = PageRequest.of(pagingRequest.getPage(), pagingRequest.getPageSize());
        Page<ScheduleWorship> pages = scheduleWorshipRepository.findAll(pageRequest);
        List<ScheduleWorshipResponse> responses = pages.getContent().stream().map(this::mappingToDto).collect(Collectors.toList());
        return new PageImpl<>(responses, pageRequest, pages.getTotalElements());
    }

    @Override
    @Transactional
    public void create(CreateScheduleWorshipRequest request) {
        MyInfoResponse myInfo = getMyInfo();
        if (!UserRole.ADMIN.toString().equalsIgnoreCase(myInfo.getUserRole())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Worship Type can't be created. Contact Admin!");
        }

        validateBkNotExists(request.getWorshipTypeId(), request.getStartTime(), request.getEndTime());

        ScheduleWorship scheduleWorship = new ScheduleWorship();
        BeanUtils.copyProperties(request, scheduleWorship);

        WorshipType worshipType = worshipTypeService.getEntityById(request.getWorshipTypeId());
        scheduleWorship.setWorshipType(worshipType);
        scheduleWorship.setStartTime(LocalDateTime.parse(request.getStartTime()));
        scheduleWorship.setEndTime(LocalDateTime.parse(request.getEndTime()));
        scheduleWorship.setWorshipLiturgy(request.getWorshipLiturgy());
        scheduleWorshipRepository.save(scheduleWorship);
    }

    @Override
    @Transactional
    public void update(UpdateScheduleWorshipRequest request) {
        MyInfoResponse myInfo = getMyInfo();
        if (!UserRole.ADMIN.toString().equalsIgnoreCase(myInfo.getUserRole())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Worship Type can't be created. Contact Admin!");
        }

        validateIdExists(request.getId());

        ScheduleWorship scheduleWorship = getEntityById(request.getId());
        BeanUtils.copyProperties(request, scheduleWorship);

        boolean isBkChange = !scheduleWorship.getWorshipType().getId().equalsIgnoreCase(request.getWorshipTypeId()) ||
                !scheduleWorship.getStartTime().equals(LocalDateTime.parse(request.getStartTime())) ||
                !scheduleWorship.getStartTime().equals(LocalDateTime.parse(request.getEndTime()));

        if (isBkChange) {
            validateBkNotExists(request.getWorshipTypeId(), request.getStartTime(), request.getEndTime());
        }

        WorshipType worshipType = worshipTypeService.getEntityById(request.getWorshipTypeId());
        scheduleWorship.setWorshipType(worshipType);
        scheduleWorship.setStartTime(LocalDateTime.parse(request.getStartTime()));
        scheduleWorship.setEndTime(LocalDateTime.parse(request.getEndTime()));
        scheduleWorship.setWorshipLiturgy(request.getWorshipLiturgy());
        scheduleWorshipRepository.saveAndFlush(scheduleWorship);
    }

    @Override
    @Transactional
    public void delete(String id) {
        ScheduleWorship scheduleWorship = getEntityById(id);
        scheduleWorshipRepository.delete(scheduleWorship);
    }

    @Override
    public void delete(List<String> ids) {
        for (String id: ids) {
            delete(id);
        }
    }

    private ScheduleWorshipResponse mappingToDto(ScheduleWorship scheduleWorship) {
        ScheduleWorshipResponse response = new ScheduleWorshipResponse();
        BeanUtils.copyProperties(scheduleWorship, response);
        return response;
    }

    private MyInfoResponse getMyInfo() {
        return userServiceClient.myInfo();
    }
}
