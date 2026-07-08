package com.eop.userservice.auth.service;

import com.eop.userservice.entity.User;
import io.jsonwebtoken.Claims;

public interface JwtService {

    String generateToken(User user);

    Claims extractAllClaims(String token);

    String extractUserId(String token);

    String extractRole(String token);

    Boolean isTokenValid(String token);

}

