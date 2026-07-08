package com.eop.baseservice.common.dto.user.request;

import com.eop.baseservice.common.constant.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserRequest {

    @NotBlank
    private String uid;

    @NotBlank
    private String password;

    @NotBlank
    private UserRole role;
}
