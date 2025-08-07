package com.example.bankcards.service;

import com.example.bankcards.dto.JWTAuthResponse;
import com.example.bankcards.dto.UserCreateDTO;
import com.example.bankcards.dto.SignInDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public JWTAuthResponse signUp(UserCreateDTO userCreateDTO) {
        userService.createUser(userCreateDTO);
        var user = userService.getUserByUsername(userCreateDTO.getUsername());
        return new JWTAuthResponse(jwtService.generateToken(user));
    }

    public JWTAuthResponse signIn(SignInDTO signInDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInDTO.getEmail(), signInDTO.getPassword())
            );
            var user = (org.springframework.security.core.userdetails.UserDetails) authentication.getPrincipal();
            return new JWTAuthResponse(jwtService.generateToken(user));
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password");
        }
    }
}
