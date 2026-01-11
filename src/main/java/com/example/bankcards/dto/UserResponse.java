package com.example.bankcards.dto;

import java.util.List;

public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private List<CardResponse> cards;
    private List<TransactionResponse> transactions;

    //getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public List<CardResponse> getCards() { return cards; }
    public List<TransactionResponse> getTransactions() { return transactions; }

    //setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setCards(List<CardResponse> cards) { this.cards = cards; }
    public void setTransactions(List<TransactionResponse> transactions) { this.transactions = transactions; }
}
