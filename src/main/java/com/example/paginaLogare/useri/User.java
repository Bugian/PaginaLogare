package com.example.paginaLogare.useri;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
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

    public User(String username, String password, String email) {
        this(null, username, password, email, LocalDateTime.now());
    }

    public User(Long id, String username, String password, String email, LocalDateTime accountCreatedOn) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.accountCreatedOn = accountCreatedOn;
    }
}
