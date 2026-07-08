package com.eop.eventservice.controller;

import com.eop.baseservice.common.dto.liturgy.request.CreateLiturgyGroupRequest;
import com.eop.baseservice.common.response.ResponseHelper;
import com.eop.baseservice.common.response.WebResponse;
import com.eop.eventservice.service.LiturgyGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/liturgy-groups")
public class LiturgyGroupController {

    private final LiturgyGroupService liturgyGroupService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> create(@RequestBody CreateLiturgyGroupRequest request) {
        liturgyGroupService.create(request);
        return ResponseEntity.ok(ResponseHelper.ok("Liturgy Group has been created successfully"));
    }
}
