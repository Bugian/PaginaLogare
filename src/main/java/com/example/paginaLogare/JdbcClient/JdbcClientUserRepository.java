package com.example.paginaLogare.JdbcClient;

import com.example.paginaLogare.exceptions.UserCreationException;
import com.example.paginaLogare.exceptions.UserNotFoundException;
import com.example.paginaLogare.user.User;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

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
        return jdbcClient.sql("SELECT * FROM user")
                .query(User.class)
                .list();
    }

    @Transactional
    public void create(User user) {
        try {

            var updated = jdbcClient.sql("INSERT INTO user (username, password, email, accountCreatedOn) VALUES (?, ?, ?, ?)")
                    .params(user.getUsername(), user.getPassword(), user.getEmail(), LocalDateTime.now().toString())
                    .update();
            if (updated != 1) {
                throw new UserCreationException("Failed to create user: " + user.getUsername());
            }
        } catch (DataAccessException e){
            log.error("Error while creating user with username {}: {}", user.getUsername(), e.getMessage(), e);
            throw new UserCreationException("Database error while creating user: " + user.getUsername(), e);
        }
    }

    @Transactional
    public void update(User newUser, Long id) {
        try {
            var updated = jdbcClient.sql("UPDATE user SET username = ?, password = ?, email = ? WHERE id = ?")
                    .params(newUser.getUsername(), newUser.getPassword(), newUser.getEmail(), id)
                    .update();
            if (updated != 1) {
                throw new UserNotFoundException("Failed to update user with id: " + newUser.getId());
            }
        } catch (DataAccessException e){
            log.error("Error while updating user with username {}: {}", newUser.getUsername(), e.getMessage(), e);
            throw new UserNotFoundException("Database error while updating user with id " + newUser.getId());
        }
    }

    public int count() {
        return jdbcClient.sql("SELECT COUNT(*) FROM user")
                .query(Integer.class)
                .single();
    }

    public Optional<User> findById(Long id) {
        try {
            return jdbcClient.sql("SELECT id, username, password, email, accountCreatedOn FROM user WHERE id = :id")
                    .param("id", id)
                    .query(User.class)
                    .optional();
        } catch (DataAccessException e){
            log.error("Error while finding user with id {}: {}", id, e.getMessage(), e);
            throw new UserNotFoundException("User with id " + id + " not found");
        }
    }

    public Optional<User> findByEmail(String email) {
        try {
        return jdbcClient.sql("SELECT id, username, password, email, accountCreatedOn FROM user WHERE email = :email")
                .param("email", email)
                .query(User.class)
                .list()
                .stream()
                .findFirst();
        } catch (DataAccessException e){
            log.error("Error while finding user with email {}: {}", email, e.getMessage(), e);
            throw new UserNotFoundException("User with email " + email + " not found");
        }
    }

    public Optional<User> findByUsername(String username) {
        try {
            return jdbcClient.sql("SELECT id, username, password, email, accountCreatedOn FROM user WHERE username = :username")
                    .param("username", username)
                    .query(User.class)
                    .list()
                    .stream()
                    .findFirst();
        } catch (DataAccessException e){
            log.error("Error while finding user with username {}: {}", username, e.getMessage(), e);
            throw new UserNotFoundException("User with username " + username + " not found");
        }
    }

    public void deleteById(Long id) {
        try {
            var updated = jdbcClient.sql("DELETE FROM user WHERE id = :id")
                    .param("id", id)
                    .update();
            if (updated != 1) {
                throw new UserNotFoundException("User not found with id: " + id);
            }
        } catch (DataAccessException e){
            log.error("Error while deleting user with id {}: {}", id, e.getMessage(), e);
            throw new UserNotFoundException("Unable to find user with id " + id);
        }
    }

    public void deleteByUsername(String username) {
        try {
            var updated = jdbcClient.sql("DELETE FROM user WHERE username = :username")
                    .param("username", username)
                    .update();
            if (updated != 1) {
                throw new UserNotFoundException("User not found with username: " + username);
            }
        } catch (DataAccessException e){
            log.error("Error while deleting user with username {}: {}", username, e.getMessage(), e);
            throw new UserNotFoundException("Unable to find user with username " + username);
        }
    }

    public void deleteByEmail(String email) {
        try {
            var updated = jdbcClient.sql("DELETE FROM user WHERE email = :email")
                    .param("email", email)
                    .update();
            if (updated != 1) {
                throw new UserNotFoundException("User not found with email: " + email);
            }
        } catch (DataAccessException e){
            log.error("Error while deleting user with email {}: {}", email, e.getMessage(), e);
            throw new UserNotFoundException("Unable to find user with email " + email);
        }
    }

    public void saveAll(List<User> users) {
        try {
            users.forEach(this::create);
        } catch (DataAccessException e){
            log.error("Error while saving multiple users", e);
            throw new RuntimeException("Database error while saving multiple users", e);
        }
    }
}
