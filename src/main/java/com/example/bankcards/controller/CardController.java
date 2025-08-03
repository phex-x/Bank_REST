package com.example.bankcards.controller;

import com.example.bankcards.dto.CardCreateDTO;
import com.example.bankcards.dto.CardResponseDTO;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CardResponseDTO createCard(@RequestBody @Valid CardCreateDTO cardCreateDTO) {
        return cardService.createCard(cardCreateDTO);
    }

    @GetMapping("/{id}")
    public CardResponseDTO getCard(@PathVariable Long id) {
        return cardService.getCardById(id);
    }

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
    }

    @PatchMapping("/{id}/status")
    public CardResponseDTO updateStatus(@PathVariable Long id, @RequestBody @Valid CardStatus status) {
        return cardService.changeStatus(id, status);
    }
}
