package com.example.sendlyApp.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class User {
    @Id
    private String id;
    private String phoneNumber;
    private String email;
    private String username;
    private String pinCode;
    private boolean isVerified;
    private Wallet wallet;
    private LocalDateTime createdAt = LocalDateTime.now();

}
