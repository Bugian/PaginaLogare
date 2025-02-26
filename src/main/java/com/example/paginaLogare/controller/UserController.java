package com.example.paginaLogare.controller;

import com.example.paginaLogare.user.User;
import com.example.paginaLogare.user.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public void create(@Valid @RequestBody User user) {
        log.info("Create user: {}", user);
        userService.create(user);
    }

    @PutMapping("/save")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void save(@Valid @RequestBody User user) {
        log.info("Update user: {}", user);
        userService.save(user);
    }

    @DeleteMapping("/username/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByUsername(@PathVariable String username) {
        log.info("Delete user with username: {}", username);
        userService.deleteByUsername(username);
    }

    @DeleteMapping("/email/{email}")
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
