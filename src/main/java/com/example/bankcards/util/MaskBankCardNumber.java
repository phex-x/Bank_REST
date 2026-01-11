package com.example.bankcards.util;

import org.springframework.stereotype.Component;

@Component
public class MaskBankCardNumber {
    public String maskBankCardNumber(String bankCardNumber) {
        return "**** **** **** " + bankCardNumber.substring(bankCardNumber.length() - 4);
    }
}
