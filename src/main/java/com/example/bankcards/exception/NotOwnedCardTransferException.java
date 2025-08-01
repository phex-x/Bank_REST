package com.example.bankcards.exception;

public class NotOwnedCardTransferException extends RuntimeException {
    public NotOwnedCardTransferException(String message) {
        super(message);
    }
}
