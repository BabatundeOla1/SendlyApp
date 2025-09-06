package com.example.sendlyApp.services;

import com.example.sendlyApp.data.model.Wallet;
import com.example.sendlyApp.data.repositories.UserRepository;
import com.example.sendlyApp.data.repositories.WalletRepository;
import com.example.sendlyApp.dto.request.CreateWalletRequest;
import com.example.sendlyApp.dto.response.CreateWalletResponse;
import com.example.sendlyApp.utils.exceptions.UserNotVerifiedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletServiceImplementation implements WalletService {

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public CreateWalletResponse createWallet(CreateWalletRequest createWalletRequest) {
        boolean isUserVerified = createWalletRequest.getUser().isVerified();
        if (!isUserVerified){
            throw new UserNotVerifiedException("User is not verified, Can not create wallet for user");
        }
        if (createWalletRequest.getUser().getWallet() != null) {
            throw new IllegalStateException("Wallet already exists for this user");
        }


        String accountNumber = createWalletRequest.getUser().getPhoneNumber().substring(1);

        Wallet newWallet = new Wallet();
        newWallet.setAccountNumber(accountNumber);
        newWallet.setUsername(createWalletRequest.getUser().getUsername());
        newWallet.setBalance(BigDecimal.ZERO);

        Wallet savedWallet = walletRepository.save(newWallet);
        createWalletRequest.getUser().setWallet(savedWallet);
        userRepository.save(createWalletRequest.getUser());

        CreateWalletResponse response = new CreateWalletResponse();
        response.setMessage("Wallet created for new user");

        return response;
    }
}
