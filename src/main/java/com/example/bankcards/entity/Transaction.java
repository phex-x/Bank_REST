package com.example.bankcards.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_card_id", nullable = false)
    private Card cardFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_card_id", nullable = false)
    private Card cardTo;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    //getters
    public Long getId() { return id; }
    public User getUser() { return user; }
    public Card getCardFrom() { return cardFrom; }
    public Card getCardTo() { return cardTo; }
    public BigDecimal getAmount() { return amount; }
    public LocalDate getDate() { return date; }

    //setters
    public void setId(Long id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setCardFrom(Card cardFrom) { this.cardFrom = cardFrom; }
    public void setCardTo(Card cardTo) { this.cardTo = cardTo; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setDate(LocalDate date) { this.date = date; }
}
