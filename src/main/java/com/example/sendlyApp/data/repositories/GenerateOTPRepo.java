package com.example.sendlyApp.data.repositories;

import com.example.sendlyApp.data.model.GenerateOtp;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface GenerateOTPRepo extends MongoRepository<GenerateOtp, String> {

    Optional<GenerateOtp> findByOtpCode(String code);
    GenerateOtp findByUserEmail(String email);
    void deleteByExpirationTimeBefore(LocalDateTime expirationTime);
    void deleteByUserEmail(String email);

}
