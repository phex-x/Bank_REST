package com.example.bankcards.service;

import com.example.bankcards.dto.CardCreateDTO;
import com.example.bankcards.dto.CardResponseDTO;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.util.mapper.CardMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardServiceTest {
    @Mock
    private CardRepository cardRepository;
    @Mock
    private CardMapper cardMapper;
    @InjectMocks
    private CardService cardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCard_shouldReturnMaskedCardNumber() {
        CardCreateDTO dto = new CardCreateDTO();
        dto.setEncryptedCardNumber("1234567890123456");
        dto.setBalance(BigDecimal.TEN);
        dto.setExpirationDate(LocalDateTime.now().plusYears(1));
        User user = new User();
        dto.setOwner(user);

        var card = new com.example.bankcards.entity.Card();
        card.setEncryptedCardNumber(dto.getEncryptedCardNumber());
        card.setMaskedCardNumber("**** **** **** 3456");
        card.setOwner(user);
        card.setBalance(dto.getBalance());
        card.setExpirationDate(dto.getExpirationDate());

        when(cardMapper.toEntity(dto)).thenReturn(card);
        when(cardRepository.save(card)).thenReturn(card);
        CardResponseDTO responseDTO = new CardResponseDTO();
        responseDTO.setMaskedNumber(card.getMaskedCardNumber());
        when(cardMapper.toDTO(card)).thenReturn(responseDTO);

        CardResponseDTO result = cardService.createCard(dto);
        assertEquals("**** **** **** 3456", result.getMaskedNumber());
    }
}