package com.example.bankcards.dto;

import com.example.bankcards.entity.User;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CardCreateDTO {
    @NotBlank
    private String encryptedCardNumber;

    @NotBlank
    private User owner;

    @NotBlank
    @Future
    private LocalDateTime expirationDate;

    @NotBlank
    private BigDecimal balance;
}
