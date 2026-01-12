package com.example.bankcards.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "to_block")
public class ToBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BlockRequestStatus status = BlockRequestStatus.PENDING;

    @Column(name = "request_date_time")
    private LocalDateTime requestDateTime = LocalDateTime.now();

    @Column(name = "processed_date_time")
    private LocalDateTime processedDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private User admin;

    @Column(name = "admin_comment", length = 500)
    private String adminComment;

    //getters
    public Long getId() { return id; }
    public User getUser() { return user; }
    public Card getCard() { return card; }
    public BlockRequestStatus getStatus() { return status; }
    public LocalDateTime getRequestDateTime() { return requestDateTime; }
    public LocalDateTime getProcessedDateTime() { return processedDateTime; }
    public User getAdmin() { return admin; }
    public String getAdminComment() { return adminComment; }

    //setters
    public void setId(Long id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setCard(Card card) { this.card = card; }
    public void setStatus(BlockRequestStatus status) { this.status = status; }
    public void setRequestDateTime(LocalDateTime requestDateTime) { this.requestDateTime = requestDateTime; }
    public void setProcessedDateTime(LocalDateTime processedDateTime) { this.processedDateTime = processedDateTime; }
    public void setAdmin(User admin) { this.admin = admin; }
    public void setAdminComment(String adminComment) { this.adminComment = adminComment; }
}
