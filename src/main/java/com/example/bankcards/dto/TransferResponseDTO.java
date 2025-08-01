package com.example.bankcards.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransferResponseDTO {
    private Long id;
    private String MaskedSourceCard;
    private String MaskedDestinationCard;
    private BigDecimal amount;
    private LocalDateTime timestamp;
}
