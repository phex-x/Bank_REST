package com.example.bankcards.controller;

import com.example.bankcards.dto.CardFindByNumberRequest;
import com.example.bankcards.dto.CardResponse;
import com.example.bankcards.entity.User;
import com.example.bankcards.service.CardService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/user/card")
public class UserController {
    private final CardService cardService;

    public UserController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<Page<CardResponse>> getAllUserCards(Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assert auth != null;
        User user = (User) auth.getPrincipal();
        assert user != null;

        return ResponseEntity.ok(cardService.getAllUserCards(user.getId(), pageable));
    }

    @GetMapping("/find")
    public ResponseEntity<Page<CardResponse>> findUserCards(
            Pageable pageable,
            @Valid @RequestBody CardFindByNumberRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assert auth != null;
        User user = (User) auth.getPrincipal();
        assert user != null;

        return ResponseEntity.ok(cardService.getAllUserCards(user.getId(), pageable));
    }

    @GetMapping("/get-balance/{id}")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assert auth != null;
        User user = (User) auth.getPrincipal();
        assert user != null;

        return ResponseEntity.ok(cardService.getBalance(user.getId(), id));
    }

}
