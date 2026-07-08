package com.eop.attendanceservice.service.impl;

import com.eop.attendanceservice.client.UserClient;
import com.eop.attendanceservice.service.AttendanceService;
import com.eop.baseservice.common.response.WebResponse;
import com.eop.baseservice.common.dto.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final UserClient userClient;

    @Override
    public UserResponse getUserId(String userId) {
        ResponseEntity<WebResponse<UserResponse>> response = userClient.getUserById(userId);
        return response.getBody().getData();
    }
}
