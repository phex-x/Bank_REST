package com.example.bankcards.repository;

import com.example.bankcards.entity.ToBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToBlockRepository extends JpaRepository<ToBlock, Long> {
}
