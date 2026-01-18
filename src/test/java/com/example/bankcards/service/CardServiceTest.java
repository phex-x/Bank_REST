package com.example.bankcards.service;

import com.example.bankcards.dto.CardCrateRequest;
import com.example.bankcards.dto.CardResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Status;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.util.CardMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.BadCredentialsException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    private CardRepository cardRepository;
    
    @Mock
    private CardMapper cardMapper;

    @InjectMocks
    private CardService cardService;

    private Card card;

    @BeforeEach
    void setUp() {
        card = new Card();
        card.setId(1L);
        card.setStatus(Status.ACTIVE);
        card.setBalance(new BigDecimal("100.00"));
    }

    @Test
    void createCard_throwsWhenNumberExists() {
        CardCrateRequest request = new CardCrateRequest();
        // assuming setter exists, otherwise mapper can be stubbed independently
        Mockito.when(cardRepository.findByNumber(any())).thenReturn(Optional.of(new Card()));

        assertThrows(BadCredentialsException.class, () -> cardService.createCard(request));
        verify(cardRepository, never()).save(any());
    }

    @Test
    void createCard_success() {
        CardCrateRequest request = new CardCrateRequest();
        CardResponse response = new CardResponse();

        when(cardRepository.findByNumber(any())).thenReturn(Optional.empty());
        when(cardMapper.toCard(any(CardCrateRequest.class))).thenReturn(card);
        when(cardMapper.toCardResponse(any(Card.class))).thenReturn(response);

        CardResponse result = cardService.createCard(request);

        assertNotNull(result);
        verify(cardRepository).save(card);
        verify(cardMapper).toCardResponse(card);
    }

    @Test
    void getCard_notFound() {
        when(cardRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(BadCredentialsException.class, () -> cardService.getCard(1L));
    }

    @Test
    void changeStatus_updatesAndReturns() {
        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
        when(cardMapper.toCardResponse(card)).thenReturn(new CardResponse());

        CardResponse res = cardService.changeStatus(1L, Status.BLOCKED);

        assertNotNull(res);
        assertEquals(Status.BLOCKED, card.getStatus());
        verify(cardRepository).save(card);
    }

    @Test
    void getAllCards_mapsPage() {
        PageRequest pageable = PageRequest.of(0, 10);
        when(cardRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(card)));
        when(cardMapper.toCardResponse(card)).thenReturn(new CardResponse());

        Page<CardResponse> page = cardService.getAllCards(pageable);
        assertEquals(1, page.getTotalElements());
    }

    @Test
    void getAllUserCards_mapsPage() {
        PageRequest pageable = PageRequest.of(0, 10);
        when(cardRepository.findAllByUserId(10L, pageable)).thenReturn(new PageImpl<>(List.of(card)));
        when(cardMapper.toCardResponse(card)).thenReturn(new CardResponse());

        Page<CardResponse> page = cardService.getAllUserCards(10L, pageable);
        assertEquals(1, page.getTotalElements());
    }

    @Test
    void findByMaskedCardNumber_checksOwnership() {
        CardResponse response = new CardResponse();
        com.example.bankcards.entity.User owner = mock(com.example.bankcards.entity.User.class);
        when(owner.getId()).thenReturn(2L);
        card.setUser(owner);

        when(cardRepository.findByMaskedCardNumber("****1234")).thenReturn(Optional.of(card));

        assertThrows(BadCredentialsException.class, () -> cardService.findByMaskedCardNumber(1L, "****1234"));

        // correct user
        when(owner.getId()).thenReturn(1L);
        when(cardMapper.toCardResponse(card)).thenReturn(response);
        CardResponse ok = cardService.findByMaskedCardNumber(1L, "****1234");
        assertSame(response, ok);
    }

    @Test
    void getBalance_checksOwnership() {
        com.example.bankcards.entity.User owner = mock(com.example.bankcards.entity.User.class);
        when(owner.getId()).thenReturn(2L);
        card.setUser(owner);
        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));

        assertThrows(BadCredentialsException.class, () -> cardService.getBalance(1L, 1L));

        when(owner.getId()).thenReturn(1L);
        BigDecimal balance = cardService.getBalance(1L, 1L);
        assertEquals(new BigDecimal("100.00"), balance);
    }
}
