package com.example.bankcards.controller;

import com.example.bankcards.dto.JWTAuthResponse;
import com.example.bankcards.dto.UserCreateDTO;
import com.example.bankcards.dto.SignInDTO;
import com.example.bankcards.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;

@Service
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public JWTAuthResponse signUp(@RequestBody @Valid UserCreateDTO userCreateDTO) {
        return authenticationService.signUp(userCreateDTO);
    }

    @PostMapping("/sign-in")
    public JWTAuthResponse signIn(@RequestBody @Valid SignInDTO signInDTO) {
        return authenticationService.signIn(signInDTO);
    }
}
