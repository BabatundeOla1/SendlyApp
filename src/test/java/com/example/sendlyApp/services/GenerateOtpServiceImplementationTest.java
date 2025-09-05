package com.example.sendlyApp.services;


import com.example.sendlyApp.data.repositories.GenerateOTPRepo;
import com.example.sendlyApp.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GenerateOtpServiceImplementationTest {

    @Autowired
    private GenerateOTPRepo generateOTPRepo;
    @Autowired
    private GenerateOtpService generateOtpService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

}