package com.example.bankcards.util;

import com.example.bankcards.dto.LoginRequest;
import com.example.bankcards.dto.RegistrationRequest;
import com.example.bankcards.dto.UserResponse;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.PasswordsDontMatchException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    private final CardMapper cardMapper;
    private final TransactionMapper transactionMapper;

    public UserMapper(PasswordEncoder passwordEncoder, CardMapper cardMapper, TransactionMapper transactionMapper) {
        this.passwordEncoder = passwordEncoder;
        this.cardMapper = cardMapper;
        this.transactionMapper = transactionMapper;
    }
    public User toEntity(RegistrationRequest dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        if (isPasswordMatches(dto.getPassword(), dto.getPasswordConfirm())) {
            user.setHashedPassword(passwordEncoder.encode(dto.getPassword()));
        } else throw new PasswordsDontMatchException("password don't match");

        return user;
    }

    public LoginRequest toLoginRequest(User user) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(user.getEmail());
        loginRequest.setPassword(user.getHashedPassword());

        return loginRequest;
    }

    public UserResponse toUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setCards(user.getCards().stream()
                .map(cardMapper::toCardResponse)
                .collect(Collectors.toList()));
        userResponse.setTransactions(user.getTransactions().stream()
                .map(transactionMapper::toTransactionResponse)
                .collect(Collectors.toList()));

        return userResponse;
    }

    private boolean isPasswordMatches(String password1, String password2) {
        return password1.equals(password2);
    }
}
