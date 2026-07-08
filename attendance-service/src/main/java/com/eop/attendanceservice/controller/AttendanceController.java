package com.eop.attendanceservice.controller;

import com.eop.attendanceservice.service.AttendanceService;
import com.eop.baseservice.common.dto.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping("/check/{id}")
    public UserResponse checkUser(@PathVariable String id) {
        return attendanceService.getUserId(id);
    }
}
