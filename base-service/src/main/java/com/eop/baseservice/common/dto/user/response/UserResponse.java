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
    private String uid;
    private String role;
    private String status;
    private ZonedDateTime lastLoginInfo;
    private Long version;
}
