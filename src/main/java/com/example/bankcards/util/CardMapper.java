package com.example.bankcards.util;

import com.example.bankcards.dto.CardResponse;
import com.example.bankcards.entity.Card;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {
    public CardResponse toCardResponse(Card card) {
        CardResponse cardResponse = new CardResponse();
        cardResponse.setId(card.getId());
        cardResponse.setMaskedNumber(card.getMaskedCardNumber());
        cardResponse.setBalance(card.getBalance());
        cardResponse.setExpiryDate(card.getExpiryDate());
        cardResponse.setStatus(card.getStatus());

        return cardResponse;
    }
}
