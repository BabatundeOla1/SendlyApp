package com.example.sendlyApp.services;

import com.example.sendlyApp.data.model.User;
import com.example.sendlyApp.data.repositories.UserRepository;
import com.example.sendlyApp.dto.request.LoginRequest;
import com.example.sendlyApp.dto.request.UserRegisterRequest;
import com.example.sendlyApp.dto.response.GenerateOtpResponse;
import com.example.sendlyApp.dto.response.LoginResponse;
import com.example.sendlyApp.dto.response.UserRegisterResponse;
import com.example.sendlyApp.utils.exceptions.InvalidCredentialsException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GenerateOtpService generateOtpService;
    @Autowired
    private EmailServices emailServices;
    @Autowired
    private WalletService walletService;

    @Override
    public UserRegisterResponse register(UserRegisterRequest userRegisterRequest) {
        validatePhoneNumber(userRegisterRequest.getPhoneNumber());

        userRegisterRequest.setPhoneNumber(normalizePhoneNumber(userRegisterRequest.getPhoneNumber()));

        checkExistingUser(userRegisterRequest);

        capitalizeUsername(userRegisterRequest);

        User newUser = modelMapper.map(userRegisterRequest, User.class);
        userRepository.save(newUser);

        UserRegisterResponse userRegisterResponse = modelMapper.map(newUser, UserRegisterResponse.class);
        userRegisterResponse.setMessage("Verification code has been sent to your mail");

        GenerateOtpResponse generatedOtpCode = generateOtpService.generateOtpCode(newUser.getEmail());
        emailServices.sendOtpEmail(newUser.getEmail(), generatedOtpCode.getOtpCode());

        return userRegisterResponse;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        loginValidation(loginRequest);
        LoginResponse response = new LoginResponse();
        response.setMessage("Login Successful.");
        return response;
    }

    private void loginValidation(LoginRequest loginRequest) {
        String username = loginRequest.getUsername().substring(0, 1).toUpperCase() + loginRequest.getUsername().substring(1).toLowerCase();
        User foundUser = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid Username or Password"));

        boolean foundUserPinCode = foundUser.getPinCode().equals(loginRequest.getPincode());
        if (!foundUserPinCode){
            throw new InvalidCredentialsException("Invalid Login details.");
        }
    }

    private void checkExistingUser(UserRegisterRequest userRegisterRequest) {
        boolean existingUserByEmail = userRepository.existsByEmail(userRegisterRequest.getEmail());
        boolean existingUserByUserName = userRepository.existsByUsername(userRegisterRequest.getUsername());
        boolean existingUserByPhoneNumber = userRepository.existsByPhoneNumber(userRegisterRequest.getPhoneNumber());

        if (existingUserByEmail) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (existingUserByUserName) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (existingUserByPhoneNumber) {
            throw new IllegalArgumentException("Phone number already exists");
        }
    }

    private String normalizePhoneNumber(String phoneNumber) {
        if (phoneNumber.startsWith("+234")) {
            // Remove +234 and prepend 0
            return "0" + phoneNumber.substring(4);
        }
        return phoneNumber; // already local format
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number is required");
        }

        if (phoneNumber.startsWith("+234")) {
            if (!phoneNumber.matches("\\+234\\d{10}")) {
                throw new IllegalArgumentException("Phone number must be in the format +234XXXXXXXXXX");
            }
        } else {
            if (!phoneNumber.matches("\\d{11}")) {
                throw new IllegalArgumentException("Phone number must be exactly 11 digits");
            }
        }
    }


    private static void capitalizeUsername(UserRegisterRequest userRegisterRequest) {
        String username = userRegisterRequest.getUsername();
        if (!username.isEmpty() && !username.isBlank()){
            String capitalize = username.substring(0, 1).toUpperCase() + username.substring(1).toLowerCase();
            userRegisterRequest.setUsername(capitalize);
        }
    }
}
