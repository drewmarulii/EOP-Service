package com.eop.userservice.auth.service;

import com.eop.userservice.auth.common.LoginRequest;
import com.eop.userservice.auth.common.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest loginRequest);

}
