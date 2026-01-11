package com.example.bankcards.dto;

import com.example.bankcards.entity.Status;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CardResponse {
    private Long id;
    private String maskedNumber;
    private Status status;
    private LocalDate expiryDate;
    private BigDecimal balance;

    //getters
    public Long getId() { return id; }
    public String getMaskedNumber() { return maskedNumber; }
    public Status getStatus() { return status; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public BigDecimal getBalance() { return balance; }

    //setters
    public void setId(Long id) { this.id = id; }
    public void setMaskedNumber(String maskedNumber) { this.maskedNumber = maskedNumber; }
    public void setStatus(Status status) { this.status = status; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
}
