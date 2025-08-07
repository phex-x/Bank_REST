package com.example.bankcards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.bankcards.entity.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CardRepository extends JpaRepository<Card, Long> {
    Card findByEncryptedCardNumber(String encryptedCardNumber);
    Page<Card> findAllByOwnerUsername(String username, Pageable pageable);
}
