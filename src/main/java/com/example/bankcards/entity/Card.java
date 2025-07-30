package com.example.bankcards.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cards")
@Data
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "encrypted_card_number")
    private String encryptedCardNumber;

    @Column(name = "masked_card_number", nullable = false, length = 512)
    private String maskedCardNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardStatus status;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    public String getMaskedCardNumber() {
        if (maskedCardNumber != null && maskedCardNumber.length() > 4) {
            String nums = maskedCardNumber.substring(maskedCardNumber.length() - 4);
            return "**** **** **** " + nums;
        }
        return maskedCardNumber;
    }
}
