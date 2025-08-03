package com.example.bankcards.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequestDTO {
    @NotNull
    @Positive
    private Long sourceCardId;

    @NotNull
    @Positive
    private Long destinationCardId;

    @NotNull
    @Positive
    private BigDecimal amount;
}
