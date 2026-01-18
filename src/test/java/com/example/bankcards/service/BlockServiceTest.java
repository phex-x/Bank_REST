package com.example.bankcards.service;

import com.example.bankcards.dto.BlockResponseAfterResult;
import com.example.bankcards.dto.BlockResponseBeforeResult;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Status;
import com.example.bankcards.entity.ToBlock;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.ToBlockRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.ToBlockMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlockServiceTest {

    @Mock
    private ToBlockRepository toBlockRepository;
    @Mock
    private CardRepository cardRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ToBlockMapper toBlockMapper;
    @Mock
    private CardService cardService;

    @InjectMocks
    private BlockService blockService;

    private Card card;
    private ToBlock toBlock;

    @BeforeEach
    void setup() {
        card = new Card();
        card.setId(5L);
        card.setStatus(Status.ACTIVE);
        com.example.bankcards.entity.User owner = new com.example.bankcards.entity.User();
        owner.setId(100L);
        card.setUser(owner);

        toBlock = new ToBlock();
        toBlock.setId(10L);
        toBlock.setCard(card);
        toBlock.setUser(owner);
    }

    @Test
    void requestBlock_success() {
        when(cardRepository.findById(5L)).thenReturn(Optional.of(card));
        when(userRepository.findById(100L)).thenReturn(Optional.of(mock(com.example.bankcards.entity.User.class)));
        when(toBlockMapper.toBlockResponseBeforeResult(any(ToBlock.class))).thenReturn(new BlockResponseBeforeResult());

        BlockResponseBeforeResult result = blockService.requestBlock(100L, 5L);
        assertNotNull(result);
        verify(toBlockRepository).save(any(ToBlock.class));
    }

    @Test
    void requestBlock_wrongOwner_throws() {
        when(cardRepository.findById(5L)).thenReturn(Optional.of(card));
        // current owner id mocked as 100L, request with 101L should fail
        assertThrows(BadCredentialsException.class, () -> blockService.requestBlock(101L, 5L));
    }

    @Test
    void requestBlock_inactiveCard_throws() {
        card.setStatus(Status.BLOCKED);
        when(cardRepository.findById(5L)).thenReturn(Optional.of(card));
        assertThrows(BadCredentialsException.class, () -> blockService.requestBlock(100L, 5L));
    }

    @Test
    void acceptBlock_changesCardStatus() {
        when(toBlockRepository.findById(10L)).thenReturn(Optional.of(toBlock));
        when(userRepository.findById(200L)).thenReturn(Optional.of(mock(com.example.bankcards.entity.User.class)));
        when(toBlockMapper.toBlockResponseAfterResult(any(ToBlock.class))).thenReturn(new BlockResponseAfterResult());

        BlockResponseAfterResult res = blockService.acceptBlock(200L, 10L, "ok");
        assertNotNull(res);
        verify(cardService).changeStatus(5L, Status.BLOCKED);
        verify(toBlockRepository).save(any(ToBlock.class));
    }

    @Test
    void rejectBlock_setsRejected() {
        when(toBlockRepository.findById(10L)).thenReturn(Optional.of(toBlock));
        when(userRepository.findById(200L)).thenReturn(Optional.of(mock(com.example.bankcards.entity.User.class)));
        when(toBlockMapper.toBlockResponseAfterResult(any(ToBlock.class))).thenReturn(new BlockResponseAfterResult());

        BlockResponseAfterResult res = blockService.rejectBlock(200L, 10L, "no");
        assertNotNull(res);
        verify(toBlockRepository).save(any(ToBlock.class));
    }

    @Test
    void getAllRequests_mapsPage() {
        when(toBlockRepository.findAll(PageRequest.of(0, 5))).thenReturn(new PageImpl<>(List.of(toBlock)));
        when(toBlockMapper.toBlockResponseBeforeResult(any(ToBlock.class))).thenReturn(new BlockResponseBeforeResult());

        Page<BlockResponseBeforeResult> page = blockService.getAllRequests(PageRequest.of(0,5));
        assertEquals(1, page.getTotalElements());
    }
}
