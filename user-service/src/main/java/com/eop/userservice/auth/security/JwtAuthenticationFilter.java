package com.eop.userservice.auth.security;

import com.eop.userservice.auth.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        String token = authHeader.substring(7);

        if(jwtService.isTokenValid(token)){
            String userId = jwtService.extractUserId(token);

            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                    userId,
                    null,
                    Collections.emptyList()
                );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request,response);
    }
}
