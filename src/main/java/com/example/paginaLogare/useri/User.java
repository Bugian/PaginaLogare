package com.example.paginaLogare.useri;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;


public record User(
        @Id
        Long id,

        @NotEmpty(message = "username cannot be empty")
        String username,

        @NotEmpty(message = "password cannot be empty")
        String password,

        @Email(message = "Email should be valid")
        @NotEmpty(message = "Email cannot be empty")
        String email,

        @PastOrPresent(message = "Account creation date cannot be in the future")
        LocalDateTime accountCreatedOn
) {

    public User {
        if (accountCreatedOn.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Account creation date cannot be in the future");
        }
    }
}
