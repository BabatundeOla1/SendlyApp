package com.example.sendlyApp.data.repositories;

import com.example.sendlyApp.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findUserById(String userId);
    Optional<User> findUserByUsername(String username);
    User findUserByPhoneNumber(String phoneNumber);

    User findUserByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByPhoneNumber(String phoneNumber);
    Boolean existsByUsername(String username);

    void deleteByVerifiedFalseAndCreatedAtBefore(LocalDateTime cutoff);
}
