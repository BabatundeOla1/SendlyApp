package com.example.sendlyApp.services;

import com.example.sendlyApp.dto.request.LoginRequest;
import com.example.sendlyApp.dto.request.UserRegisterRequest;
import com.example.sendlyApp.dto.response.LoginResponse;
import com.example.sendlyApp.dto.response.UserRegisterResponse;

public interface UserService {

//    UserRegistrationResponse registerUser(UserRegistrationRequest registrationRequest);
    UserRegisterResponse register(UserRegisterRequest userRegisterRequest);

    LoginResponse login(LoginRequest loginRequest);
}
