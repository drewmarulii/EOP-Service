package com.eop.eventservice.controller;

import com.eop.baseservice.common.dto.event.request.CreateWorshipTypeRequest;
import com.eop.baseservice.common.response.ResponseHelper;
import com.eop.baseservice.common.response.WebResponse;
import com.eop.eventservice.service.WorshipTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/worship-types")
public class WorshipTypeController {

    private final WorshipTypeService worshipTypeService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> create(@RequestBody CreateWorshipTypeRequest request) {
        worshipTypeService.create(request);
        return ResponseEntity.ok(ResponseHelper.ok("Worship Type has been created successfully"));
    }
}
