package com.example.bankcards.repository;

import com.example.bankcards.dto.CardResponse;
import com.example.bankcards.entity.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByNumber(String cardNumber);
    
    Page<Card> findAllByUserId(Long userId, Pageable pageable);

    Optional<Card> findByMaskedCardNumber(String maskedCardNumber);
}
