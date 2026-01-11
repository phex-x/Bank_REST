package com.example.bankcards.util;

import com.example.bankcards.dto.TransactionResponse;
import com.example.bankcards.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    private final CardMapper cardMapper;

    public TransactionMapper(CardMapper cardMapper) {
        this.cardMapper = cardMapper;
    }

    public TransactionResponse toTransactionResponse(Transaction transaction) {
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setId(transaction.getId());
        transactionResponse.setAmount(transaction.getAmount());
        transactionResponse.setTimestamp(transaction.getDate());
        transactionResponse.setCardFrom(cardMapper.toCardResponse(transaction.getCardFrom()));
        transactionResponse.setCardTo(cardMapper.toCardResponse(transaction.getCardTo()));

        return transactionResponse;
    }
}
