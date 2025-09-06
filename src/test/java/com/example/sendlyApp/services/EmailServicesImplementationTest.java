package com.example.sendlyApp.services;

import com.example.sendlyApp.data.model.GenerateOtp;
import com.example.sendlyApp.data.repositories.GenerateOTPRepo;
import com.example.sendlyApp.dto.request.VerifyOtpRequest;
import com.example.sendlyApp.dto.response.GenerateOtpResponse;
import com.example.sendlyApp.dto.response.VerifyOtpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.any;



import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmailServicesImplementationTest {


    @Autowired
    private EmailServices emailServices;
    @Autowired
    private GenerateOTPRepo  generateOTPRepo;
    @Autowired
    private GenerateOtpService generateOtpService;
    @MockitoBean
    private JavaMailSender mailSender;



//    @BeforeEach
//    void tearDown(){
//        generateOTPRepo.deleteAll();
//    }

    @Test
    void testThatOtpIsGeneratedAndSentToEmail() {
        String email = "olatheezy@gmail.com";
        GenerateOtpResponse response = generateOtpService.generateOtpCode(email);

        emailServices.sendOtpEmail(email, response.getOtpCode());

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));

    }

//    @Test
//    void testThatUserCanVerifyOtp() {
//        String email = "olatheezy@gmail.com";
//        String otpCode = "343639";
//
//        GenerateOtp otp = new GenerateOtp();
//        otp.setUserEmail(email);
//        otp.setOtpCode(otpCode);
//        otp.setExpirationTime(LocalDateTime.now().plusMinutes(5));
//        otp.setUsed(false);
//        generateOTPRepo.save(otp);
//
//        VerifyOtpRequest verifyOtpRequest = new VerifyOtpRequest();
//        verifyOtpRequest.setEmail(email);
//        verifyOtpRequest.setOtpCode(otpCode);
//
//        VerifyOtpResponse verifyOtpResponse = emailServices.verifyOtp(verifyOtpRequest);
//
//        assertNotNull(verifyOtpResponse);
//        assertEquals("Verification Successful", verifyOtpResponse.getVerificationResponse());
//    }

    @Test
    void testThatUserCanVerifyOtp() {
        String email = "olatheezy@gmail.com";

        // Generate OTP for email
        GenerateOtpResponse otpResponse = generateOtpService.generateOtpCode(email);

        VerifyOtpRequest verifyOtpRequest = new VerifyOtpRequest();
        verifyOtpRequest.setEmail(email);
        verifyOtpRequest.setOtpCode(otpResponse.getOtpCode());

        VerifyOtpResponse verifyOtpResponse = emailServices.verifyOtp(verifyOtpRequest);

        assertNotNull(verifyOtpResponse);
        assertEquals("Verification Successful", verifyOtpResponse.getVerificationResponse());
    }

}