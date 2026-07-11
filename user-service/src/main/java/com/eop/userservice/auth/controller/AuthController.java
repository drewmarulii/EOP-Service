package com.eop.userservice.auth.controller;

import com.eop.userservice.auth.common.LoginRequest;
import com.eop.userservice.auth.common.LoginResponse;
import com.eop.baseservice.common.MyInfoResponse;
import com.eop.userservice.auth.security.SecurityUtils;
import com.eop.userservice.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @GetMapping("/my-info")
    public MyInfoResponse myInfo() {
        return new MyInfoResponse (
            SecurityUtils.getUserSpeficicDetail("user_id"),
            SecurityUtils.getUserSpeficicDetail("user_role")
        );
    }
}
