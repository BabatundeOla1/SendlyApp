package com.example.sendlyApp.services;

import com.example.sendlyApp.data.model.GenerateOtp;
import com.example.sendlyApp.data.repositories.GenerateOTPRepo;
import com.example.sendlyApp.dto.response.GenerateOtpResponse;
import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class GenerateOtpServiceImplementation implements GenerateOtpService{

    private static final SecureRandom random = new SecureRandom();
    @Autowired
    private GenerateOTPRepo generateOTPRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public GenerateOtpResponse generateOtpCode(String userEmail) {
        GenerateOtp generateOtp = new GenerateOtp();
        String otpCode = String.format("%06d", random.nextInt(1_000_000));
        generateOtp.setOtpCode(otpCode);
        generateOtp.setExpirationTime(LocalDateTime.now().plusMinutes(5));

        generateOtp.setUsed(false);
        generateOtp.setUserEmail(userEmail);
        generateOTPRepository.save(generateOtp);
        return modelMapper.map(generateOtp, GenerateOtpResponse.class);
    }


}
