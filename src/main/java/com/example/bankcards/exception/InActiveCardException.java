package com.example.bankcards.exception;

public class InActiveCardException extends RuntimeException {
    public InActiveCardException(String message) {
        super(message);
    }
}
