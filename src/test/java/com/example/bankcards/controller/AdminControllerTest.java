package com.example.bankcards.controller;

import com.example.bankcards.dto.*;
import com.example.bankcards.service.BlockService;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.UserService;
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
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;

import java.util.List;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;
    @Mock
    private CardService cardService;
    @Mock
    private BlockService blockService;

    @InjectMocks
    private AdminController adminController;

    private com.example.bankcards.entity.User admin;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();

        admin = new com.example.bankcards.entity.User();
        admin.setId(7L);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(admin, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getUser_ok() throws Exception {
        when(userService.getUserById(5L)).thenReturn(new UserResponse());

        mockMvc.perform(get("/api/admin/user/{id}", 5L))
                .andExpect(status().isOk());

        verify(userService).getUserById(5L);
    }

    @Test
    void changeUserRole_ok() throws Exception {
        // no setters available -> send JSON body instead
        when(userService.changeRole(anyLong(), any())).thenReturn(new UserResponse());

        String body = "{\n  \"id\": 11,\n  \"role\": \"ADMIN\"\n}";
        mockMvc.perform(patch("/api/admin/user/change-role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        verify(userService).changeRole(eq(11L), any());
    }

    @Test
    void createCard_ok() throws Exception {
        when(cardService.createCard(any(CardCrateRequest.class))).thenReturn(new CardResponse());

        String body = "{\n  \"cardNumber\": \"4000000000000002\",\n  \"expiryDate\": \"2030-12-31\"\n}";
        mockMvc.perform(post("/api/admin/card/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        verify(cardService).createCard(any(CardCrateRequest.class));
    }

    @Test
    void toBlock_resultApproved_callsAccept() throws Exception {
        when(blockService.acceptBlock(eq(7L), eq(33L), eq("ok"))).thenReturn(new BlockResponseAfterResult());
        String body = "{\n  \"requestId\": 33,\n  \"comment\": \"ok\",\n  \"status\": \"APPROVED\"\n}";

        mockMvc.perform(post("/api/admin/to-block/result")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        verify(blockService).acceptBlock(7L, 33L, "ok");
    }

    @Test
    void getAllCards_ok() throws Exception {
        Page<CardResponse> page = new PageImpl<>(List.of(new CardResponse()), PageRequest.of(0,10), 1);
        when(cardService.getAllCards(any())).thenReturn(page);

        mockMvc.perform(get("/api/admin/card/get-all"))
                .andExpect(status().isOk());

        verify(cardService).getAllCards(any());
    }
}
