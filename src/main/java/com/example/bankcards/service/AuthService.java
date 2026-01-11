package com.example.bankcards.service;

import com.example.bankcards.dto.JwtResponse;
import com.example.bankcards.dto.LoginRequest;
import com.example.bankcards.dto.RegistrationRequest;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.InvalidTokenException;
import com.example.bankcards.exception.TokenExpiredException;
import com.example.bankcards.security.CustomUserDetailsService;
import com.example.bankcards.security.JWTService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthService {
    private final UserService userService;
    private final JWTService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationManager authenticationManager;

    private final ArrayList<String> tokenBlacklist = new ArrayList<>();

    public AuthService(UserService userService, JWTService jwtService, AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
    }

    public JwtResponse register(RegistrationRequest registrationRequest) {
        return login(userService.createUser(registrationRequest));
    }

    public JwtResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            User userDetails = (User) customUserDetailsService.loadUserByUsername(loginRequest.getEmail());
            String token = jwtService.generateToken(userDetails);

            return new JwtResponse(
                    token,
                    "bearer",
                    userDetails.getId()
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    public JwtResponse refresh(String oldToken) {
        if (tokenBlacklist.contains(oldToken)) {
            throw new TokenExpiredException("token is expired");
        }

        String email = jwtService.extractUserEmail(oldToken);

        if (email == null) {
            throw new InvalidTokenException("token is invalid");
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        if(!jwtService.isTokenValid(oldToken, userDetails)) {
            throw new InvalidTokenException("token is invalid");
        }

        tokenBlacklist.add(oldToken);

        String newToken = jwtService.generateToken(userDetails);

        User user = userService.getUserByEmail(email);

        return new JwtResponse(
                newToken,
                "bearer",
                user.getId()
        );
    }

    public void logout(String token) {
        tokenBlacklist.add(token);
        SecurityContextHolder.clearContext();
    }



}
