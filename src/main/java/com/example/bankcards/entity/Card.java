package com.example.bankcards.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_number", unique = true)
    private String number;

    @Column(name = "masked_card_number")
    private String maskedCardNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.ACTIVE;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //getters
    public Long getId() { return id; }
    public String getNumber() { return number; }
    public String getMaskedCardNumber() { return maskedCardNumber; }
    public Status getStatus() { return status; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public BigDecimal getBalance() { return balance; }
    public User getUser() { return user; }

    //setters
    public void setId(Long id) { this.id = id; }
    public void setNumber(String number) { this.number = number; }
    public void setMaskedCardNumber(String maskedCardNumber) { this.maskedCardNumber = maskedCardNumber; }
    public void setStatus(Status status) { this.status = status; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public void setUser(User user) { this.user = user; }
}
