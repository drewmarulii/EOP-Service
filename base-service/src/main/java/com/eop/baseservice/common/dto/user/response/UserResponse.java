package com.eop.baseservice.common.dto.user.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String churchName;
    private String username;
    private String role;
    private String status;
    private ZonedDateTime lastLoginInfo;
    private Boolean isActive;
    private Long version;
}
