package com.example.bankcards.service;

import com.example.bankcards.dto.CardCreateDTO;
import com.example.bankcards.dto.CardResponseDTO;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.exception.CardNotFoundException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.util.mapper.CardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    public CardResponseDTO createCard(CardCreateDTO cardCreateDTO) {
        Card card = cardMapper.toEntity(cardCreateDTO);

        Card savedCard = cardRepository.save(card);
        log.info("Card created: {}", card);

        return cardMapper.toDTO(savedCard);
    }

    public CardResponseDTO getCardById(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException("card with id: " + id + " not found"));
        log.info("Card found: {}", card);
        return cardMapper.toDTO(card);
    }

    public void deleteCard(Long id) {
        Card card = cardRepository.findById(id)
                        .orElseThrow(() -> new CardNotFoundException("card with id: " + id + " not found"));
        cardRepository.delete(card);
        log.info("Card deleted: {}", id);
    }

    public CardResponseDTO changeStatus(Long id, CardStatus status) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException("card with id: " + id + " not found"));
        card.setStatus(status);
        Card savedCard = cardRepository.save(card);
        log.info("Card updated: {}", savedCard);

        return cardMapper.toDTO(savedCard);
    }
}
