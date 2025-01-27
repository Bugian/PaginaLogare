package com.example.paginaLogare.useri;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public List<User> findAll() {
        log.info("Find all users");
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        log.info("Find user by id: {}", id);
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found with ID:" + id);
        }
        return user.get();
    }

    @GetMapping("/email/{email}")
    public User findByEmail(@PathVariable String email) {
        log.info("Find user by email: {}", email);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found with email:" + email);
        }
        return user;
    }

    @GetMapping("username/{username}")
    public User findByUsername(@PathVariable String username) {
        log.info("Find user by username: {}", username);
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found with username:" + username);
        }
        return user;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void create(@Valid @RequestBody User user) {
        log.info("Create user: {}", user);
        userRepository.create(user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @Valid @RequestBody User user) {
        log.info("Update user: {}", user);
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found with id:" + id);
        }
        userRepository.update(user, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        log.info("Delete user with id: {}", id);
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found with id:" + id);
        }
        userRepository.deleteById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/username/{username}")
    public void deleteByUsername(@PathVariable String username) {
        log.info("Delete user with username: {}", username);
        userRepository.deleteByUsername(username);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/email/{email}")
    public void deleteByEmail(@PathVariable String email) {
        log.info("Delete user with email: {}", email);
        userRepository.deleteByEmail(email);
    }

    @GetMapping("/count")
    public int count() {
        log.info("Count users");
        return userRepository.count();
    }

//    @ResponseStatus(HttpStatus.CREATED)
//    @PutMapping("/api/users/{id}")
//    public void save(@Valid @RequestBody User user) {
//        log.info("Save user: {}", user);
//        userRepository.save(user);
//    }

}
