package com.example.paginaLogare.useri;

import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class UserRestClient {

    private final RestClient restClient;

    public UserRestClient(String baseUrl) {
        this.restClient = RestClient.create(baseUrl);
    }

    public List<User> findAll() {
        return Arrays.asList(Objects.requireNonNull(restClient.get().uri("/").retrieve().body(User[].class)));
    }

    public User findById(Long id) {
        return restClient.get().uri("/{id}", id).retrieve().body(User.class);
    }

    public User findByUsername(String username) {
        return restClient.get().uri("/{username}", username).retrieve().body(User.class);
    }

    public User findByEmail(String email) {
        return restClient.get().uri("/{email}", email).retrieve().body(User.class);
    }

    public void create(User user) {
        restClient.post().uri("/").body(user).retrieve().toBodilessEntity();
    }

    public void update(Long id, User user) {
        restClient.put().uri("/{id}", id).body(user).retrieve().toBodilessEntity();
    }

    public void save(User user) {

    }

    public void deleteById(Long id) {
        restClient.delete().uri("/{id}", id).retrieve().toBodilessEntity();
    }

    public void deleteByUsername(String username) {
        restClient.delete().uri("/{username}", username).retrieve().toBodilessEntity();
    }

    public void deleteByEmail(String email) {
        restClient.delete().uri("/{email}", email).retrieve().toBodilessEntity();
    }

    public int count() {
        return Objects.requireNonNull(restClient.get().uri("/count").retrieve().body(Integer.class));
    }
}
