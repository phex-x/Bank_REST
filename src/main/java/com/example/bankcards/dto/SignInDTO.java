package com.example.bankcards.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignInDTO {
    @NotBlank
    @Email(message = "email must be like: user@email.com")
    private String email;

    @NotBlank
    @Size(min = 8, message = "password must be at least 8 symbols")
    private String password;
}
