package com.eop.userservice.controller;

import com.eop.baseservice.common.dto.user.request.UpdateUserPersonRequest;
import com.eop.baseservice.common.dto.user.response.UserPersonResponse;
import com.eop.baseservice.common.response.PagingRequest;
import com.eop.baseservice.common.response.ResponseHelper;
import com.eop.baseservice.common.response.WebResponse;
import com.eop.userservice.service.UserPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-profiles")
public class UserPersonController {

    private final UserPersonService userPersonService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<UserPersonResponse>> getById(@PathVariable String id) {
        return ResponseEntity.ok(ResponseHelper.ok(userPersonService.getById(id)));
    }

    @GetMapping
    public ResponseEntity<WebResponse<List<UserPersonResponse>>> getAllByPagingAndSearch(
            PagingRequest pagingRequest, @RequestParam(required = false) String inquiry) {
        return ResponseEntity.ok(ResponseHelper.ok(pagingRequest,
                userPersonService.getAllByPagingAndSearch(pagingRequest, inquiry)));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> update(@RequestBody UpdateUserPersonRequest request) {
        userPersonService.update(request);
        return ResponseEntity.ok(ResponseHelper.ok("User Profile has been updated successfully"));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> deleteById(@PathVariable String id) {
        userPersonService.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok("User Profile has been deleted"));
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> deleteByIds(@RequestBody List<String> ids) {
        userPersonService.delete(ids);
        return ResponseEntity.ok(ResponseHelper.ok("User Profiles has been deleted"));
    }
}
