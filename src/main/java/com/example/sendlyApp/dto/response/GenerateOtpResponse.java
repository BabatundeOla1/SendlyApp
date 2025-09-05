package com.example.sendlyApp.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GenerateOtpResponse {
    private String otpCode;
    private LocalDateTime expirationTime;
}