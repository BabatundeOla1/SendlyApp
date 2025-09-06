package com.example.sendlyApp.services;

import com.example.sendlyApp.dto.request.CreateWalletRequest;
import com.example.sendlyApp.dto.response.CreateWalletResponse;

public interface WalletService {

    CreateWalletResponse createWallet(CreateWalletRequest createWalletRequest);
}
