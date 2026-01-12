package com.example.bankcards.dto;

import com.example.bankcards.entity.BlockRequestStatus;

import java.time.LocalDateTime;

public class BlockResponseBeforeResult {
    private Long id;
    private UserResponse user;
    private CardResponse card;
    private LocalDateTime requestDateTime;
    private BlockRequestStatus status;

    //getters
    public Long getId() { return id; }
    public UserResponse getUser() { return user; }
    public CardResponse getCard() { return card; }
    public LocalDateTime getRequestDateTime() { return requestDateTime; }
    public BlockRequestStatus getStatus() { return status; }

    //setters
    public void setId(Long id) { this.id = id; }
    public void setUser(UserResponse user) { this.user = user; }
    public void setCard(CardResponse card) { this.card = card; }
    public void setRequestDateTime(LocalDateTime requestDateTime) { this.requestDateTime = requestDateTime; }
    public void setStatus(BlockRequestStatus status) { this.status = status; }
}
