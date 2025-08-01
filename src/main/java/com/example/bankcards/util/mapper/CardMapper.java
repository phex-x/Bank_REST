package com.example.bankcards.util.mapper;

import com.example.bankcards.dto.CardCreateDTO;
import com.example.bankcards.dto.CardResponseDTO;
import com.example.bankcards.entity.Card;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {

    public Card toEntity(CardCreateDTO cardCreateDTO) {
        Card card = new Card();
        card.setEncryptedCardNumber(cardCreateDTO.getEncryptedCardNumber());
        card.setBalance(cardCreateDTO.getBalance());
        card.setExpirationDate(cardCreateDTO.getExpirationDate());
        card.setOwner(cardCreateDTO.getOwner());

        return card;
    }

    public CardResponseDTO toDTO(Card card) {
        CardResponseDTO cardResponseDTO = new CardResponseDTO();
        cardResponseDTO.setId(card.getId());
        cardResponseDTO.setMaskedNumber(card.getMaskedCardNumber());
        cardResponseDTO.setOwner(card.getOwner());
        cardResponseDTO.setStatus(card.getStatus().name());
        cardResponseDTO.setBalance(card.getBalance());
        cardResponseDTO.setExpirationDate(card.getExpirationDate());

        return cardResponseDTO;
    }
}
