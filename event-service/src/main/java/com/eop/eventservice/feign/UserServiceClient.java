package com.eop.eventservice.feign;

import com.eop.baseservice.common.MyInfoResponse;
import com.eop.baseservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-service", url = "${user-service.url}", configuration = FeignConfig.class)
public interface UserServiceClient {

    @GetMapping("/api/auth/my-info")
    MyInfoResponse myInfo();

}
