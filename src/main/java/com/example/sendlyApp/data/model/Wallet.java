package com.example.sendlyApp.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
public class Wallet {
    @Id
    private String id;
    private BigDecimal balance = BigDecimal.ZERO;
    private String accountNumber;
    private String username;
}
