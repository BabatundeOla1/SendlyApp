package com.example.sendlyApp.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GenerateOtpRequest {
    private String otpCode;
    private LocalDateTime expirationTime;
    private boolean isUsed;

}
