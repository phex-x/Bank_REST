package com.example.bankcards.service;

import com.example.bankcards.dto.TransactionRequest;
import com.example.bankcards.dto.TransactionResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Status;
import com.example.bankcards.entity.Transaction;
import com.example.bankcards.exception.InActiveCardException;
import com.example.bankcards.exception.InsufficientFundsException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.TransactionRepository;
import com.example.bankcards.util.TransactionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private CardRepository cardRepository;
    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionService transactionService;

    private Card cardFrom;
    private Card cardTo;

    @BeforeEach
    void init() {
        cardFrom = new Card();
        cardTo = new Card();
        cardFrom.setId(1L);
        cardTo.setId(2L);
        cardFrom.setStatus(Status.ACTIVE);
        cardTo.setStatus(Status.ACTIVE);
        cardFrom.setBalance(new BigDecimal("100.00"));
        cardTo.setBalance(new BigDecimal("5.00"));
        com.example.bankcards.entity.User owner = mock(com.example.bankcards.entity.User.class);
        when(owner.getId()).thenReturn(10L);
        cardFrom.setUser(owner);
        cardTo.setUser(owner);
    }

    @Test
    void transfer_success_updatesBalancesAndSaves() {
        TransactionRequest request = mock(TransactionRequest.class);
        when(request.getCardFromId()).thenReturn(1L);
        when(request.getCardToId()).thenReturn(2L);
        when(request.getAmount()).thenReturn(new BigDecimal("20.00"));

        when(cardRepository.findById(1L)).thenReturn(Optional.of(cardFrom));
        when(cardRepository.findById(2L)).thenReturn(Optional.of(cardTo));
        when(transactionMapper.toTransactionResponse(any(Transaction.class))).thenReturn(new TransactionResponse());

        TransactionResponse response = transactionService.transfer(10L, request);
        assertNotNull(response);
        assertEquals(new BigDecimal("80.00"), cardFrom.getBalance());
        assertEquals(new BigDecimal("25.00"), cardTo.getBalance());
        verify(cardRepository, times(1)).save(cardFrom);
        verify(cardRepository, times(1)).save(cardTo);
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void transfer_inactiveCardFrom_throws() {
        cardFrom.setStatus(Status.BLOCKED);
        TransactionRequest request = mock(TransactionRequest.class);
        when(request.getCardFromId()).thenReturn(1L);

        when(cardRepository.findById(1L)).thenReturn(Optional.of(cardFrom));

        assertThrows(InActiveCardException.class, () -> transactionService.transfer(10L, request));
    }

    @Test
    void transfer_insufficientFunds_throws() {
        TransactionRequest request = mock(TransactionRequest.class);
        when(request.getCardFromId()).thenReturn(1L);
        when(request.getCardToId()).thenReturn(2L);
        when(request.getAmount()).thenReturn(new BigDecimal("200.00"));

        when(cardRepository.findById(1L)).thenReturn(Optional.of(cardFrom));
        when(cardRepository.findById(2L)).thenReturn(Optional.of(cardTo));

        assertThrows(InsufficientFundsException.class, () -> transactionService.transfer(10L, request));
    }
}
