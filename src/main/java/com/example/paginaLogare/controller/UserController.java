package com.example.paginaLogare.controller;

import com.example.paginaLogare.user.User;
import com.example.paginaLogare.user.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("")
    public List<User> findAll() {
        log.info("Find all users");
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        log.info("Find user by id: {}", id);
        return userService.findUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @GetMapping("/email/{email}")
    public User findByEmail(@PathVariable String email) {
        log.info("Find user by email: {}", email);
        return userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    @GetMapping("username/{username}")
    public User findByUsername(@PathVariable String username) {
        log.info("Find user by username: {}", username);
        return userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody User user) {
        try{
        log.info("Create user: {}", user);
        userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();

            if (e.getMessage().contains("Username already exists")) {
                errorResponse.put("error", "Username already exists");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);  
            } else if (e.getMessage().contains("Email already exists")) {
                errorResponse.put("error", "Email already exists");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
            } else {
                errorResponse.put("error", "Body need to contain only username, password and email");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody User user) {
        try {
            log.info("Updating user with id: {}", id);
            
            // Verify the ID in path matches the ID in request body
            if (!id.equals(user.getId())) {
                throw new RuntimeException("ID in path does not match ID in request body");
            }
            
            userService.save(user);
            return ResponseEntity.noContent().build();
            
        } catch (RuntimeException e) {
            log.error("Error updating user: {}", e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            } else if (e.getMessage().contains("already exists")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
            } else if (e.getMessage().contains("does not match")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }
        }
    }

    @DeleteMapping("id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        log.info("Delete user with id: {}", id);
        userService.deleteById(id);
    }

    @DeleteMapping("username/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByUsername(@PathVariable String username) {
        log.info("Delete user with username: {}", username);
        userService.deleteByUsername(username);
    }

    @DeleteMapping("email/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByEmail(@PathVariable String email) {
        log.info("Delete user with email: {}", email);
        userService.deleteByEmail(email);
    }

    @GetMapping("/count")
    public long count() {
        log.info("Count users");
        return userService.count();
    }

    @PostMapping("/login")
    public boolean login(@RequestParam String username, @RequestParam String password) {
        log.info("Login user: {}", username);
        return userService.checkPassword(username, password);
    }

}
