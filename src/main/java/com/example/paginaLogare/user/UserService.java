package com.example.paginaLogare.user;

import com.example.paginaLogare.components.PasswordHasher;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordHasher passwordHasher;

    public UserService(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void create(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        String hashedPassword = passwordHasher.hash(user.getPassword());
        user.setPassword(hashedPassword);
        user.setAccountCreatedOn(LocalDateTime.now());
        userRepository.save(user);
    }

    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    public long count() {
        return userRepository.count();
    }

    public boolean checkPassword(String username, String rawPassword) {
        return userRepository.findByUsername(username)
                .map(user -> passwordHasher.checkPassword(rawPassword, user.getPassword()))
                .orElse(false);
    }

    public void save(@Valid User user) {
        userRepository.save(user);
    }
}
