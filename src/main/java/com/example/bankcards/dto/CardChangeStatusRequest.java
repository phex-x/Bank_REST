package com.example.bankcards.dto;

import com.example.bankcards.entity.Status;

public class CardChangeStatusRequest {
    private Long id;
    private Status status;

    //getters
    public Long getId() { return id; }
    public Status getStatus() { return status; }
}
