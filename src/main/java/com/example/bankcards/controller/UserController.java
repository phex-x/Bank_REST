package com.example.bankcards.controller;

import com.example.bankcards.dto.CardFindByNumberRequest;
import com.example.bankcards.dto.CardResponse;
import com.example.bankcards.dto.TransactionRequest;
import com.example.bankcards.dto.TransactionResponse;
import com.example.bankcards.entity.User;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final CardService cardService;
    private final TransactionService transactionService;

    public UserController(CardService cardService, TransactionService transactionService) {
        this.cardService = cardService;
        this.transactionService = transactionService;
    }

    @GetMapping("/card/get-all")
    public ResponseEntity<Page<CardResponse>> getAllUserCards(Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assert auth != null;
        User user = (User) auth.getPrincipal();
        assert user != null;

        return ResponseEntity.ok(cardService.getAllUserCards(user.getId(), pageable));
    }

    @GetMapping("/card/find")
    public ResponseEntity<Page<CardResponse>> findUserCards(
            Pageable pageable,
            @Valid @RequestBody CardFindByNumberRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assert auth != null;
        User user = (User) auth.getPrincipal();
        assert user != null;

        return ResponseEntity.ok(cardService.getAllUserCards(user.getId(), pageable));
    }

    @GetMapping("/card/get-balance/{id}")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assert auth != null;
        User user = (User) auth.getPrincipal();
        assert user != null;

        return ResponseEntity.ok(cardService.getBalance(user.getId(), id));
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(@Valid @RequestBody TransactionRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assert auth != null;
        User user = (User) auth.getPrincipal();
        assert user != null;

        return ResponseEntity.ok(transactionService.transfer(user.getId(), request));
    }

}
