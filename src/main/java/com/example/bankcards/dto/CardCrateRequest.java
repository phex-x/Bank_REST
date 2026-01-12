package com.example.bankcards.dto;

import com.example.bankcards.entity.User;

import java.time.LocalDate;

public class CardCrateRequest {
    private String cardNumber;
    private User user;
    private LocalDate expiryDate;

    //getters
    public String getCardNumber() { return cardNumber; }
    public User getUser() { return user; }
    public LocalDate getExpiryDate() { return expiryDate; }
}
