package com.example.bankcards.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LoginRequest {
    @Email(message = "email must be valid")
    private String email;

    @NotBlank(message = "password can't be null")
    @Size(min = 8, message = "password must be at least 8 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
    )
    private String password;

    //getters
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    //setters
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
}
