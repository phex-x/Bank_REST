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

    @PrePersist
    public void prePersist() {
        if (this.balance == null) {
            this.balance = BigDecimal.ZERO;
        }

        if (this.encryptedCardNumber != null && this.encryptedCardNumber.length() > 4) {
            this.maskedCardNumber = "**** **** **** " + this.encryptedCardNumber.substring(this.encryptedCardNumber.length() - 4);
        } else throw new IllegalArgumentException("invalid card number");

        if (this.owner == null) {
            throw new IllegalArgumentException("card owner must be set");
        }

        if (this.status == null) {
            this.status = CardStatus.ACTIVE;
        }
    }
}
