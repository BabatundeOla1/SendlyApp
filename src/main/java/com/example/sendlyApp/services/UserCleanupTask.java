package com.example.sendlyApp.services;

import com.example.sendlyApp.data.repositories.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class UserCleanupTask {

    private final UserRepository userRepository;

    public UserCleanupTask(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Runs every 5 minutes
    @Scheduled(fixedRate = 300000)
    public void deleteUnverifiedUsers() {
        LocalDateTime cutoff = LocalDateTime.now().minusMinutes(10);
        userRepository.deleteByVerifiedFalseAndCreatedAtBefore(cutoff);
    }
}
