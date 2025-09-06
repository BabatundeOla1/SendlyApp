package com.example.sendlyApp.dto.request;

import com.example.sendlyApp.data.model.User;
import lombok.Data;

@Data
public class CreateWalletRequest {
    private User user;
}
