package com.example.bankcards.controller;

import com.example.bankcards.dto.CardCreateDTO;
import com.example.bankcards.dto.CardResponseDTO;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping("/card/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CardResponseDTO createCard(@RequestBody @Valid CardCreateDTO cardCreateDTO) {
        return cardService.createCard(cardCreateDTO);
    }

    @GetMapping("/card/{id}")
    public CardResponseDTO getCard(@PathVariable Long id) {
        return cardService.getCardById(id);
    }

    @DeleteMapping("/card/{id}")
    public ResponseEntity<String> deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.ok("card deleted successfully");
    }

    @PatchMapping("/card/{id}")
    public CardResponseDTO updateStatus(@PathVariable Long id, @RequestBody @Valid CardStatus status) {
        return cardService.changeStatus(id, status);
    }
}
