package com.eop.userservice.controller;

import com.eop.baseservice.common.constant.UserStatus;
import com.eop.baseservice.common.dto.user.request.CreateUserRequest;
import com.eop.baseservice.common.dto.user.request.UpdateUserRequest;
import com.eop.baseservice.common.response.PagingRequest;
import com.eop.baseservice.common.response.ResponseHelper;
import com.eop.baseservice.common.response.WebResponse;
import com.eop.baseservice.common.dto.user.response.UserResponse;
import com.eop.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<UserResponse>> getById(@PathVariable String id) {
        return ResponseEntity.ok(ResponseHelper.ok(userService.getById(id)));
    }

    @GetMapping
    public ResponseEntity<WebResponse<List<UserResponse>>> getAllByPagingAndSearch(
        PagingRequest pagingRequest, @RequestParam(required = false) String inquiry) {
        return ResponseEntity.ok(ResponseHelper.ok(pagingRequest,
            userService.getAllByPagingAndSearch(pagingRequest, inquiry)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> create(@RequestBody CreateUserRequest request) {
        userService.create(request);
        return ResponseEntity.ok(ResponseHelper.ok("User has been created successfully"));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> update(@RequestBody UpdateUserRequest request) {
        userService.update(request);
        return ResponseEntity.ok(ResponseHelper.ok("User has been updated successfully"));
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> deleteById(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok("User has been deleted"));
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> deleteByIds(@RequestBody List<String> ids) {
        userService.delete(ids);
        return ResponseEntity.ok(ResponseHelper.ok("Users has been deleted"));
    }

    @PutMapping(value = "/approved", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> changeStatus(@RequestBody List<String> ids) {
        userService.changeStatus(ids, UserStatus.ACTIVE);
        return ResponseEntity.ok(ResponseHelper.ok("User has been updated successfully"));
    }
}
