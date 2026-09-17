package com.eop.userservice.controller;

import com.eop.baseservice.common.dto.user.request.CreateUserFamilyRequest;
import com.eop.baseservice.common.dto.user.request.UpdateUserFamilyRequest;
import com.eop.baseservice.common.dto.user.response.UserFamilyResponse;
import com.eop.baseservice.common.response.ResponseHelper;
import com.eop.baseservice.common.response.WebResponse;
import com.eop.userservice.service.UserFamilyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-families")
public class UserFamilyController {

    private final UserFamilyService userFamilyService;

    @GetMapping(value = "/{id}/user-person", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<UserFamilyResponse>> getByUserPersonId(@PathVariable String id) {
        return ResponseEntity.ok(ResponseHelper.ok(userFamilyService.getUserFamilyByUserPersonId(id)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> create(@RequestBody CreateUserFamilyRequest request) {
        userFamilyService.create(request);
        return ResponseEntity.ok(ResponseHelper.ok("User Family has been created successfully"));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> update(@RequestBody UpdateUserFamilyRequest request) {
        userFamilyService.update(request);
        return ResponseEntity.ok(ResponseHelper.ok("User Family has been updated successfully"));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> deleteById(@PathVariable String id) {
        userFamilyService.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok("User Family has been deleted"));
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> deleteByIds(@RequestBody List<String> ids) {
        userFamilyService.delete(ids);
        return ResponseEntity.ok(ResponseHelper.ok("User Families has been deleted"));
    }
}
