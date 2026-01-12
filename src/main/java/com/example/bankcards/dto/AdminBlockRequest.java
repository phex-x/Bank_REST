package com.example.bankcards.dto;

import com.example.bankcards.entity.BlockRequestStatus;

public class AdminBlockRequest {
    private Long requestId;
    private String comment;
    private BlockRequestStatus status;

    //getters
    public Long getRequestId() { return requestId; }
    public String getComment() { return comment; }
    public BlockRequestStatus getStatus() { return status; }
}
