package com.example.bankcards.service;

import com.example.bankcards.dto.CardCrateRequest;
import com.example.bankcards.dto.CardResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Status;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.util.CardMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    public CardService(CardRepository cardRepository, CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
    }

    public CardResponse createCard(CardCrateRequest cardCrateRequest) {
        if (cardRepository.findByNumber(cardCrateRequest.getCardNumber()).isPresent()) {
            throw new BadCredentialsException("Card number already exists");
        }

        Card card = cardMapper.toCard(cardCrateRequest);
        cardRepository.save(card);

        return cardMapper.toCardResponse(card);
    }

    public CardResponse getCard(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new BadCredentialsException("Card not found"));

        return cardMapper.toCardResponse(card);
    }

    public void deleteCard(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new BadCredentialsException("Card not found"));

        cardRepository.delete(card);
    }

    public CardResponse changeStatus(Long id, Status status) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new BadCredentialsException("Card not found"));

        card.setStatus(status);
        cardRepository.save(card);

        return cardMapper.toCardResponse(card);
    }

    public Page<CardResponse> getAllCards(Pageable pageable) {
        Page<Card> cards = cardRepository.findAll(pageable);

        return cards.map(cardMapper::toCardResponse);
    }
}
