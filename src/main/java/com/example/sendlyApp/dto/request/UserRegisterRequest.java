package com.example.sendlyApp.dto.request;

import lombok.Data;

@Data
public class UserRegisterRequest {

    private String email;
    private String phoneNumber;
    private String username;
    private String pincode;
}
