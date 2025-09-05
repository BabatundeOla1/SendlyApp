package com.example.sendlyApp.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

@Data
public class GenerateOtp{
    @Id
    private String id;
    private String otpCode;
    private LocalDateTime expirationTime;
    private boolean isUsed;
    private String userEmail;
}
