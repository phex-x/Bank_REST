package com.example.bankcards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.bankcards.entity.Card;

public interface CardRepository extends JpaRepository<Card, Long> {
}
