package com.example.bankcards.service;

import com.example.bankcards.dto.TransactionRequest;
import com.example.bankcards.dto.TransactionResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Transaction;
import com.example.bankcards.exception.InsufficientFundsException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.TransactionRepository;
import com.example.bankcards.util.TransactionMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;
    private final TransactionMapper transactionMapper;

    public TransactionService(TransactionRepository transactionRepository, CardRepository cardRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.cardRepository = cardRepository;
        this.transactionMapper = transactionMapper;
    }

    public TransactionResponse transfer(Long userId, TransactionRequest transactionRequest) {
        Card cardFrom = cardRepository.findById(transactionRequest.getCardFromId())
                .orElseThrow(() -> new RuntimeException("Card not found"));

        if (!cardFrom.getUser().getId().equals(userId)) {
            throw new BadCredentialsException("You don't have rights to this card");
        }

        Card cardTo = cardRepository.findById(transactionRequest.getCardToId())
                .orElseThrow(() -> new RuntimeException("Card not found"));

        if (!cardTo.getUser().getId().equals(userId)) {
            throw new BadCredentialsException("You don't have rights to this card");
        }

        if (cardFrom.getBalance().compareTo(transactionRequest.getAmount()) < 0) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        cardFrom.setBalance(cardFrom.getBalance().subtract(transactionRequest.getAmount()));
        cardTo.setBalance(cardTo.getBalance().add(transactionRequest.getAmount()));
        cardRepository.save(cardFrom);
        cardRepository.save(cardTo);

        Transaction transaction = new Transaction();
        transaction.setCardFrom(cardFrom);
        transaction.setCardTo(cardTo);
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setUser(cardFrom.getUser());
        transaction.setDateTime(LocalDateTime.now());
        transactionRepository.save(transaction);

        return transactionMapper.toTransactionResponse(transaction);
    }
}
