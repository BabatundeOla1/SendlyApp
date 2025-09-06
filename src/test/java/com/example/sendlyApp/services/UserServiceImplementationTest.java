package com.example.sendlyApp.services;

import com.example.sendlyApp.data.model.User;
import com.example.sendlyApp.data.repositories.UserRepository;
import com.example.sendlyApp.dto.request.LoginRequest;
import com.example.sendlyApp.dto.request.UserRegisterRequest;
import com.example.sendlyApp.dto.response.LoginResponse;
import com.example.sendlyApp.dto.response.UserRegisterResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
class UserServiceImplementationTest {

    @Autowired
    private UserRepository repository;
    @Autowired
    private UserService userService;
    @MockitoBean
    private EmailServices emailServices;
    @BeforeEach
    void setUp() {
        repository.deleteAll();;
    }

    @Test
    void testThatUserCanRegister() {
        UserRegisterRequest registerRequest = new UserRegisterRequest();
        registerRequest.setEmail("olatheezy@gmail.com");
        registerRequest.setPhoneNumber("09012345678");
        registerRequest.setUsername("theezyArt");
        registerRequest.setPincode("0000");

        doNothing().when(emailServices).sendOtpEmail(anyString(), anyString());

        UserRegisterResponse registeredUser = userService.register(registerRequest);

        assertNotNull(registeredUser);
        assertEquals("Verification code has been sent to your mail", registeredUser.getMessage());
    }
    @Test
    void testThatFirstLetterInUsernameIsCapitalized(){
        UserRegisterRequest registerRequest = new UserRegisterRequest();
        registerRequest.setEmail("olatheezy@gmail.com");
        registerRequest.setPhoneNumber("09012345678");
        registerRequest.setUsername("theezyArt");
        registerRequest.setPincode("0000");
        UserRegisterResponse registeredUser = userService.register(registerRequest);

        User savedUser = repository.findUserByEmail(registerRequest.getEmail());
        assertEquals("Theezyart", savedUser.getUsername());
    }

    @Test
    void testThatUserCanRegisterAndLogin(){
        UserRegisterRequest registerRequest = new UserRegisterRequest();
        registerRequest.setEmail("olatheezy@gmail.com");
        registerRequest.setPhoneNumber("09012345678");
        registerRequest.setUsername("theezyArt");
        registerRequest.setPincode("0000");
        UserRegisterResponse registeredUser = userService.register(registerRequest);

        User savedUser = repository.findUserByEmail(registerRequest.getEmail());
        assertEquals("Theezyart", savedUser.getUsername());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("theezyart");
        loginRequest.setPincode("0000");

        LoginResponse loginResponse = userService.login(loginRequest);
        assertEquals("Login Successful.", loginResponse.getMessage());
    }
}