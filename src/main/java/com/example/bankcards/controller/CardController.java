package com.example.bankcards.controller;

import com.example.bankcards.dto.CardCreateDTO;
import com.example.bankcards.dto.CardResponseDTO;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    @GetMapping("/my")
    @PreAuthorize("hasRole('USER')")
    public Page<CardResponseDTO> getMyCards(Pageable pageable) {
        return cardService.getMyCards(pageable);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<CardResponseDTO> getAllCards(Pageable pageable) {
        return cardService.getAllCards(pageable);
    }
}
