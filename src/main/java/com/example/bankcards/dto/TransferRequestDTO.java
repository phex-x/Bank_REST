package com.example.bankcards.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequestDTO {
    @NotBlank
    private Long sourceCardId;

    @NotBlank
    private Long destinationCardId;

    @NotBlank
    private BigDecimal amount;
}
