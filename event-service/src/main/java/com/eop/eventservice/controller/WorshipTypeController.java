package com.eop.eventservice.controller;

import com.eop.baseservice.common.dto.event.request.CreateWorshipTypeRequest;
import com.eop.baseservice.common.dto.event.request.UpdateWorshipTypeRequest;
import com.eop.baseservice.common.dto.event.response.WorshipTypeResponse;
import com.eop.baseservice.common.response.PagingRequest;
import com.eop.baseservice.common.response.ResponseHelper;
import com.eop.baseservice.common.response.WebResponse;
import com.eop.eventservice.service.WorshipTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/worship-types")
public class WorshipTypeController {

    private final WorshipTypeService worshipTypeService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<WorshipTypeResponse>> getById(@PathVariable String id) {
        return ResponseEntity.ok(ResponseHelper.ok(worshipTypeService.getById(id)));
    }

    @GetMapping
    public ResponseEntity<WebResponse<List<WorshipTypeResponse>>> getAllByPagingAndSearch(
            PagingRequest pagingRequest, @RequestParam(required = false) String inquiry,  @RequestParam(name = "isActive") Boolean isActive) {
        return ResponseEntity.ok(ResponseHelper.ok(pagingRequest, worshipTypeService.getAllByPagingAndSearch(pagingRequest, inquiry, isActive)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> create(@RequestBody CreateWorshipTypeRequest request) {
        worshipTypeService.create(request);
        return ResponseEntity.ok(ResponseHelper.ok("Worship Type has been created successfully"));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> update(@RequestBody UpdateWorshipTypeRequest request) {
        worshipTypeService.update(request);
        return ResponseEntity.ok(ResponseHelper.ok("Worship Type has been updated successfully"));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> deleteById(@PathVariable String id) {
        worshipTypeService.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok("Worship Type has been deleted"));
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> deleteByIds(@RequestBody List<String> ids) {
        worshipTypeService.delete(ids);
        return ResponseEntity.ok(ResponseHelper.ok("Worship Type has been deleted"));
    }
}
