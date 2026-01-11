package com.example.bankcards.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {
    private Long id;
    private CardResponse cardFrom;
    private CardResponse cardTo;
    private BigDecimal amount;
    private LocalDateTime timestamp;

    //getters
    public Long getId() { return id; }
    public CardResponse getCardFrom() { return cardFrom; }
    public CardResponse getCardTo() { return cardTo; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }

    //setters
    public void setId(Long id) { this.id = id; }
    public void setCardFrom(CardResponse cardFrom) { this.cardFrom = cardFrom; }
    public void setCardTo(CardResponse cardTo) { this.cardTo = cardTo; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
