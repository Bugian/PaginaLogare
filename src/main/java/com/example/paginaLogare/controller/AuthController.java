package com.example.paginaLogare.controller;

import com.example.paginaLogare.components.PasswordHasher;
import com.example.paginaLogare.request.LoginRequest;
import com.example.paginaLogare.user.UserRepository;
import com.example.paginaLogare.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    @Autowired
    public AuthController(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        String username = loginRequest.getUsername();

        if (email == null && username == null) {
            return ResponseEntity.badRequest().body("Email or username is required");
        }

        Optional<User> userOpt;
        if(email != null) {
            userOpt = userRepository.findByEmail(email);
        } else {
            userOpt = userRepository.findByUsername(username);
        }

        if(userOpt.isPresent()){
            User user = userOpt.get();
            if (passwordHasher.checkPassword(password, user.getPassword())) {
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.badRequest().body("Invalid credentials");
            }
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }



}
