package com.example.sendlyApp.services;

import com.example.sendlyApp.dto.response.GenerateOtpResponse;

public interface GenerateOtpService {
    GenerateOtpResponse generateOtpCode(String userEmail);
}
