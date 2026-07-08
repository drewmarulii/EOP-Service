package com.eop.userservice.auth.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {

    private String accessToken;
    private String uid;
    private String role;

}
