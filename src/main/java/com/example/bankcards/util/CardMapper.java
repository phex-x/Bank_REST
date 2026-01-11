package com.example.bankcards.util;

import com.example.bankcards.dto.CardCrateRequest;
import com.example.bankcards.dto.CardResponse;
import com.example.bankcards.entity.Card;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {
    private final MaskBankCardNumber mask;

    public CardMapper(MaskBankCardNumber mask) {
        this.mask = mask;
    }

    public CardResponse toCardResponse(Card card) {
        CardResponse cardResponse = new CardResponse();
        cardResponse.setId(card.getId());
        cardResponse.setMaskedNumber(card.getMaskedCardNumber());
        cardResponse.setBalance(card.getBalance());
        cardResponse.setExpiryDate(card.getExpiryDate());
        cardResponse.setStatus(card.getStatus());

        return cardResponse;
    }

    public Card toCard(CardCrateRequest cardCrateRequest) {
        Card card = new Card();
        card.setUser(cardCrateRequest.getUser());
        card.setNumber(cardCrateRequest.getCardNumber());
        card.setMaskedCardNumber(mask.maskBankCardNumber(cardCrateRequest.getCardNumber()));
        card.setExpiryDate(cardCrateRequest.getExpiryDate());

        return card;
    }
}
