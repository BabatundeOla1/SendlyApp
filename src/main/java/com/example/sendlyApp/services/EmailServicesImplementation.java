package com.example.sendlyApp.services;

import com.example.sendlyApp.data.model.GenerateOtp;
import com.example.sendlyApp.data.model.User;
import com.example.sendlyApp.data.repositories.GenerateOTPRepo;
import com.example.sendlyApp.data.repositories.UserRepository;
import com.example.sendlyApp.dto.request.CreateWalletRequest;
import com.example.sendlyApp.dto.request.VerifyOtpRequest;
import com.example.sendlyApp.dto.response.VerifyOtpResponse;
import com.example.sendlyApp.utils.exceptions.EmailNotFoundException;
import com.example.sendlyApp.utils.exceptions.OtpExpiredException;
import com.example.sendlyApp.utils.exceptions.OtpNotCorrectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class EmailServicesImplementation implements EmailServices{

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private GenerateOTPRepo generateOTPRepo;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WalletService walletService;


    public void sendOtpEmail(String email, String otpCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your Verification Code");
        message.setText("Your OTP code is: " + otpCode + "\n\nIt will expire in 5 minutes.");

        try{
            mailSender.send(message);
        }
        catch(Exception e){
            System.err.println("Failed to send email to " + email);
            e.printStackTrace();
            throw new RuntimeException("Error while sending OTP email", e);
        }
    }

    @Override
    public VerifyOtpResponse verifyOtp(VerifyOtpRequest verifyOtpRequest) {
        GenerateOtp foundOtp = generateOTPRepo.findByUserEmail(verifyOtpRequest.getEmail());
        validateOtp(verifyOtpRequest, foundOtp);

        User foundUser = userRepository.findUserByEmail(foundOtp.getUserEmail());

        foundOtp.setUsed(true);
        generateOTPRepo.save(foundOtp);
        foundUser.setVerified(true);
        userRepository.save(foundUser);

        CreateWalletRequest verifiedUserWallet = new CreateWalletRequest();
        verifiedUserWallet.setUser(foundUser);

        walletService.createWallet(verifiedUserWallet);

        VerifyOtpResponse response = new VerifyOtpResponse();
        response.setVerificationResponse("Verification Successful");
        return response;
    }

    private static void validateOtp(VerifyOtpRequest verifyOtpRequest, GenerateOtp foundOtp) {
        if (foundOtp == null) {
            throw new EmailNotFoundException("Email not found, kindly register first.");
        }
        if(foundOtp.isUsed()){
            throw new OtpExpiredException("Otp has been used, try generate a new one");
        }

        boolean isOtpExpired = foundOtp != null && foundOtp.getExpirationTime().isBefore(LocalDateTime.now());

        if (!foundOtp.getOtpCode().equals(verifyOtpRequest.getOtpCode())){
            throw new OtpNotCorrectException("Invalid Otp.");
        }
        if (isOtpExpired) {
            throw new OtpExpiredException("OTP has expired");
        }
    }

}
