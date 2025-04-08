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
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
            userRepository.delete(user);
    }

    public void deleteByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
            userRepository.delete(user);
    }

    public void deleteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
            userRepository.delete(user);
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
        if (user.getId() == null) {
            throw new RuntimeException("User ID cannot be null for update");
        }
    
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + user.getId()));
    
        // Check for duplicate username (excluding current user)
        userRepository.findByUsername(user.getUsername())
                .ifPresent(u -> {
                    if (!u.getId().equals(user.getId())) {
                        throw new RuntimeException("Username already exists");
                    }
                });
    
        // Check for duplicate email (excluding current user)
        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    if (!u.getId().equals(user.getId())) {
                        throw new RuntimeException("Email already exists");
                    }
                });
    
        // Only hash if password has changed
        if (!user.getPassword().equals(existingUser.getPassword())) {
            String hashedPassword = passwordHasher.hash(user.getPassword());
            user.setPassword(hashedPassword);
        }
    
        userRepository.save(user);
    }
}
