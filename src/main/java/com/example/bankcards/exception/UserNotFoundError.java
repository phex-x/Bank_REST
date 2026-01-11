package com.example.bankcards.exception;

public class UserNotFoundError extends RuntimeException {
    public UserNotFoundError(String message) {
        super(message);
    }
}
