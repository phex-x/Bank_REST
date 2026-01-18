package com.example.bankcards.controller;

import com.example.bankcards.dto.BlockResponseBeforeResult;
import com.example.bankcards.dto.CardResponse;
import com.example.bankcards.service.BlockService;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.TransactionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CardService cardService;
    @Mock
    private TransactionService transactionService;
    @Mock
    private BlockService blockService;

    @InjectMocks
    private UserController userController;

    private com.example.bankcards.entity.User principal;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        principal = new com.example.bankcards.entity.User();
        principal.setId(42L);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(principal, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @AfterEach
    void clearAuth() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getAllUserCards_returnsOk() throws Exception {
        Page<CardResponse> page = new PageImpl<>(List.of(new CardResponse()), PageRequest.of(0,10), 1);
        when(cardService.getAllUserCards(eq(42L), any())).thenReturn(page);

        mockMvc.perform(get("/api/user/card/get-all").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(cardService).getAllUserCards(eq(42L), any());
    }

    @Test
    void getBalance_returnsOk_andDelegatesWithCurrentArgs() throws Exception {
        when(cardService.getBalance(eq(42L), eq(5L))).thenReturn(new BigDecimal("10.00"));

        mockMvc.perform(get("/api/user/card/get-balance/{id}", 5L))
                .andExpect(status().isOk());

        verify(cardService).getBalance(eq(42L), eq(5L));
    }

    @Test
    void requestBlock_returnsOk() throws Exception {
        when(blockService.requestBlock(eq(42L), eq(5L))).thenReturn(new BlockResponseBeforeResult());

        mockMvc.perform(post("/api/user/request-block/{id}", 5L))
                .andExpect(status().isOk());

        verify(blockService).requestBlock(42L, 5L);
    }
}
