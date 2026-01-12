package com.example.bankcards.dto;

import java.math.BigDecimal;

public class TransactionRequest {
    private Long cardFromId;
    private Long cardToId;
    private BigDecimal amount;

    //getters
    public Long getCardFromId() { return cardFromId; }
    public Long getCardToId() { return cardToId; }
    public BigDecimal getAmount() { return amount; }
}
