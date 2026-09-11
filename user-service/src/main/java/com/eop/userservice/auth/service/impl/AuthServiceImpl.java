package com.eop.userservice.auth.service.impl;

import com.eop.userservice.auth.common.LoginRequest;
import com.eop.userservice.auth.common.LoginResponse;
import com.eop.userservice.auth.service.AuthService;
import com.eop.userservice.auth.service.JwtService;
import com.eop.userservice.entity.User;
import com.eop.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credential not matched");
        }

        String token = jwtService.generateToken(user);

        return new LoginResponse(
            token,
            user.getUsername(),
            user.getRole().toString()
        );
    }
}
