package com.example.bankcards.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateDTO {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "username can consist only letters and numbers")
    @Size(min = 4, message = "username must be at least 4 symbols")
    private String username;

    @NotBlank
    @Size(min = 8, message = "password must be at least 8 symbols")
    private String password;
}
