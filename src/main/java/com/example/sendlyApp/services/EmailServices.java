package com.example.sendlyApp.services;

import com.example.sendlyApp.dto.request.VerifyOtpRequest;
import com.example.sendlyApp.dto.response.VerifyOtpResponse;

public interface EmailServices {
    void sendOtpEmail(String mail, String otpCode);

    VerifyOtpResponse verifyOtp(VerifyOtpRequest verifyOtpRequest);
}
