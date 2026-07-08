package com.eop.attendanceservice.service;

import com.eop.baseservice.common.dto.user.response.UserResponse;

public interface AttendanceService {

    UserResponse getUserId(String userId);

}
