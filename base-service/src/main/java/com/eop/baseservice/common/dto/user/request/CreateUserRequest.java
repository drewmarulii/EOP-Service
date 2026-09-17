package com.eop.baseservice.common.dto.user.request;

import com.eop.baseservice.common.constant.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    private String username;
    private String password;
    private UserRole role;

    private CreateUserPersonRequest userPersonRequest;
}
