package com.example.paginaLogare.useri;


import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;


@Repository
public class InMemoryUserRepository implements UserRepository {

    private final List<User> users = new ArrayList<>();
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final AtomicLong idGenerator = new AtomicLong(1);
    private final Queue<Long> availableIds = new LinkedList<>();

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    @Override
    public void create(User user) {
        Long generatedId = availableIds.isEmpty() ? idGenerator.getAndIncrement() : availableIds.poll();
        User newUser = new User(
                generatedId,
                user.username(),
                user.password(),
                user.email(),
                LocalDateTime.now());
        users.add(newUser);
    }

    @Override
    public void update(User newUser, Long id) {
        Optional<User> existingUser = findById(id);
        if (existingUser.isPresent()) {
            var r = existingUser.get();
            log.info("Updating Existing User: {}", existingUser.get());
            users.set(users.indexOf(r), newUser);

        }
    }

    @Override
    public int count() {
        return users.size();
    }

    @Override
    public void save(User user) {
        Long generatedId = idGenerator.getAndIncrement();
        User newUser = new User(
                generatedId,
                user.username(),
                user.password(),
                user.email(),
                user.accountCreatedOn()
        );
        users.add(newUser);
    }

    @Override
    public void saveAll(List<User> usersToSave) {
        usersToSave.forEach(this::create);
    }

    @Override
    public Optional<User> findById(Long id) {
        return users.stream().filter(user ->
                        user.id().equals(id))
                .findFirst();
    }

    @Override
    public User findByEmail(String email) {
        return users.stream().filter(user ->
                user.email().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return users.stream().filter(user ->
                user.username().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting Run By Id: {}", id);
        boolean removed = users.removeIf(user -> user.id().equals(id));
        if (removed) {
            availableIds.add(id);
        }
    }

    @Override
    public void deleteByUsername(String username) {
        log.info("Deleting Run By Username: {}", username);
        Optional<User> userToDelete = users.stream()
                        .filter(user -> user.username().equals(username))
                                .findFirst();
        userToDelete.ifPresent(user -> {
            users.remove(user);
            availableIds.add(user.id());
        });
    }

    @Override
    public void deleteByEmail(String email) {
        log.info("Deleting Run By Email: {}", email);
        Optional<User> userToDelete = users.stream()
                        .filter(user -> user.email().equals(email))
                                .findFirst();
        userToDelete.ifPresent(user -> {
            users.remove(user);
            availableIds.add(user.id());
        });
    }

    @PostConstruct
    private void init() {
        users.add(new User(
                idGenerator.getAndIncrement(),
                "anton",
                "password",
                "anton@mail.ru",
                LocalDateTime.now()));

        users.add(new User(
                idGenerator.getAndIncrement(),
                "Wednesday",
                "pass",
                "wednesway@mail.ru",
                LocalDateTime.now()));
    }
}
