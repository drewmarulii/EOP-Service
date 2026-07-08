package com.eop.userservice.auth.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    private String uid;
    private String password;

}
