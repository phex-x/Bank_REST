package com.example.bankcards.dto;

import com.example.bankcards.entity.BlockRequestStatus;

import java.time.LocalDateTime;

public class BlockResponseAfterResult {
    private Long id;
    private UserResponse user;
    private CardResponse card;
    private LocalDateTime requestDateTime;
    private BlockRequestStatus status;
    private UserResponse admin;
    private LocalDateTime processedDateTime;
    private String adminComment;

    //getters
    public Long getId() { return id; }
    public UserResponse getUser() { return user; }
    public CardResponse getCard() { return card; }
    public LocalDateTime getRequestDateTime() { return requestDateTime; }
    public BlockRequestStatus getStatus() { return status; }
    public UserResponse getAdmin() { return admin; }
    public LocalDateTime getProcessedDateTime() { return processedDateTime; }
    public String getAdminComment() { return adminComment; }

    //setters
    public void setId(Long id) { this.id = id; }
    public void setUser(UserResponse user) { this.user = user; }
    public void setCard(CardResponse card) { this.card = card; }
    public void setRequestDateTime(LocalDateTime requestDateTime) { this.requestDateTime = requestDateTime; }
    public void setStatus(BlockRequestStatus status) { this.status = status; }
    public void setAdmin(UserResponse admin) { this.admin = admin; }
    public void setProcessedDateTime(LocalDateTime processedDateTime) { this.processedDateTime = processedDateTime; }
    public void setAdminComment(String adminComment) { this.adminComment = adminComment; }
}
