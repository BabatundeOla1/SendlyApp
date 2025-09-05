package com.example.sendlyApp.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class User {
    @Id
    private String id;
    private String phoneNumber;
    private String email;
    private String username;
    private String pinCode;
    private boolean isVerified;
}
