package com.example.paginaLogare.useri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcClientUserRepository {

    private static final Logger log = LoggerFactory.getLogger(JdbcClientUserRepository.class);
    private final JdbcClient jdbcClient;

    public JdbcClientUserRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<User> findAll() {
        return jdbcClient.sql("SELECT * FROM users")
                .query(User.class)
                .list();
    }

    public void create(User user) {
        var updated = jdbcClient.sql("INSERT INTO users (username, password, email, accountCreatedOn) VALUES (?, ?, ?, ?)")
                .params(user.username(), user.password(), user.email(), LocalDateTime.now().toString())
                .update();

        Assert.state(updated == 1, "Failed to create user: " + user.username());
    }

    public void update(User newUser, Long id) {
        var updated = jdbcClient.sql("UPDATE users SET username = ?, password = ?, email = ? WHERE id = ?")
                .params(newUser.username(), newUser.password(), newUser.email(), id)
                .update();

        Assert.state(updated == 1, "Failed to update user with id: " + id);
    }

    public int count() {
        return jdbcClient.sql("SELECT COUNT(*) FROM users")
                .query(Integer.class)
                .single();
    }

    public Optional<User> findById(Long id) {
        return jdbcClient.sql("SELECT id, username, password, email, accountCreatedOn FROM users WHERE id = :id")
                .param("id", id)
                .query(User.class)
                .optional();
    }

    public User findByEmail(String email) {
        return jdbcClient.sql("SELECT id, username, password, email, accountCreatedOn FROM users WHERE email = :email")
                .param("email", email)
                .query(User.class)
                .list()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public User findByUsername(String username) {
        return jdbcClient.sql("SELECT id, username, password, email, accountCreatedOn FROM users WHERE username = :username")
                .param("username", username)
                .query(User.class)
                .list()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public void deleteById(Long id) {
        var updated = jdbcClient.sql("DELETE FROM users WHERE id = :id")
                .param("id", id)
                .update();

        Assert.state(updated == 1, "Failed to delete user with id: " + id);
    }

    public void deleteByUsername(String username) {
        var updated = jdbcClient.sql("DELETE FROM users WHERE username = :username")
                .param("username", username)
                .update();

        Assert.state(updated == 1, "Failed to delete user with username: " + username);
    }

    public void deleteByEmail(String email) {
        var updated = jdbcClient.sql("DELETE FROM users WHERE email = :email")
                .param("email", email)
                .update();

        Assert.state(updated == 1, "Failed to delete user with email: " + email);
    }

    public void saveAll(List<User> users) {
        users.forEach(this::create);
    }
}
