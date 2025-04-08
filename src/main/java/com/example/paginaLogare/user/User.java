package com.example.paginaLogare.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "accountCreatedOn", nullable = false, updatable = false)
    private LocalDateTime accountCreatedOn;

    public User() {
    }

    public User(Long id, String username, String password, String email, LocalDateTime accountCreatedOn) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.accountCreatedOn = accountCreatedOn;
    }
}