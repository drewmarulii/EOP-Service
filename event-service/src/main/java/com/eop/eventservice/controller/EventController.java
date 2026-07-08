package com.eop.eventservice.controller;

import com.eop.baseservice.common.dto.event.request.CreateEventRequest;
import com.eop.baseservice.common.dto.user.request.CreateUserRequest;
import com.eop.baseservice.common.response.ResponseHelper;
import com.eop.baseservice.common.response.WebResponse;
import com.eop.eventservice.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> create(@RequestBody CreateEventRequest request) {
        eventService.create(request);
        return ResponseEntity.ok(ResponseHelper.ok("Event has been created successfully"));
    }

    @PostMapping(value = "/{id}/generate-poster", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> generatePoster(@PathVariable String id) {
        String poster = eventService.generatePoster(id);
        return ResponseEntity.ok(ResponseHelper.ok("Event poster has been created successfully" + poster));
    }
}
