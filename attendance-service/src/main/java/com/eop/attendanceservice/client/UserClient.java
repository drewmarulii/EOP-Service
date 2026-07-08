package com.eop.attendanceservice.client;

import com.eop.baseservice.common.response.WebResponse;
import com.eop.baseservice.common.dto.user.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/users/{id}")
    ResponseEntity<WebResponse<UserResponse>> getUserById(@PathVariable("id") String id);

}
