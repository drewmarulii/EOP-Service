package com.eop.eventservice.controller;

import com.eop.baseservice.common.dto.event.request.CreateScheduleWorshipRequest;
import com.eop.baseservice.common.dto.event.request.UpdateScheduleWorshipRequest;
import com.eop.baseservice.common.dto.event.response.ScheduleWorshipResponse;
import com.eop.baseservice.common.response.PagingRequest;
import com.eop.baseservice.common.response.ResponseHelper;
import com.eop.baseservice.common.response.WebResponse;
import com.eop.eventservice.service.ScheduleWorshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule-worships")
public class ScheduleWorshipController {

    private final ScheduleWorshipService scheduleWorshipService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<ScheduleWorshipResponse>> getById(@PathVariable String id) {
        return ResponseEntity.ok(ResponseHelper.ok(scheduleWorshipService.getById(id)));
    }

    @GetMapping
    public ResponseEntity<WebResponse<List<ScheduleWorshipResponse>>> getAllByPagingAndSearch(
            PagingRequest pagingRequest, @RequestParam(required = false) String inquiry, @RequestParam(name = "isActive") Boolean isActive) {
        return ResponseEntity.ok(ResponseHelper.ok(pagingRequest, scheduleWorshipService.getAllByPagingAndSearch(pagingRequest, inquiry, isActive)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> create(@RequestBody CreateScheduleWorshipRequest request) {
        scheduleWorshipService.create(request);
        return ResponseEntity.ok(ResponseHelper.ok("Schedule Worship has been created successfully"));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> update(@RequestBody UpdateScheduleWorshipRequest request) {
        scheduleWorshipService.update(request);
        return ResponseEntity.ok(ResponseHelper.ok("Schedule Worship has been updated successfully"));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> deleteById(@PathVariable String id) {
        scheduleWorshipService.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok("Schedule Worship has been deleted"));
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> deleteByIds(@RequestBody List<String> ids) {
        scheduleWorshipService.delete(ids);
        return ResponseEntity.ok(ResponseHelper.ok("Schedule Worship has been deleted"));
    }

}
