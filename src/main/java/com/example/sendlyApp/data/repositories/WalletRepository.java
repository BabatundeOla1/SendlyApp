package com.example.sendlyApp.data.repositories;

import com.example.sendlyApp.data.model.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WalletRepository extends MongoRepository<Wallet, String> {
    Optional<Wallet> findWalletById(String walletId);

}
