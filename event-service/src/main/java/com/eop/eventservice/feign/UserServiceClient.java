package com.eop.eventservice.feign;

import com.eop.baseservice.common.MyInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-service", url = "${user-service.url}")
public interface UserServiceClient {

    @GetMapping("/api/auth/my-info")
    MyInfoResponse myInfo();

}
