package com.example.bankcards.dto;

import com.example.bankcards.entity.User;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CardResponseDTO {
    private Long id;
    private String maskedNumber;
    private User owner;
    private String status;
    private BigDecimal balance;
    private LocalDateTime expirationDate;
}
