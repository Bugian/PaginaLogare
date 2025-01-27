package com.example.paginaLogare.useri.webClient;

import com.example.paginaLogare.useri.User;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class UserWebClient implements UserHttpClient{

    private final WebClient webClient;

    public UserWebClient(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    public List<User> findAll() {
        return Arrays.asList(Objects.requireNonNull(
                webClient.get()
                        .uri("/")
                        .retrieve()
                        .bodyToMono(User[].class)
                        .block()
        ));
    }

    public User findById(Long id) {
        return webClient.get()
                    .uri("/{id}", id)
                    .retrieve()
                    .bodyToMono(User.class)
                    .block();
    }

    public User findByUsername(String username) {
        return webClient.get()
                .uri("/{username}", username)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }

    public User findByEmail(String email) {
        return webClient.get()
                .uri("/{email}", email)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }

    public void create(User user) {
        webClient.post()
                .uri("/")
                .bodyValue(user)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void update(Long id, User user) {
        webClient.put()
                .uri("/{id}", id)
                .bodyValue(user)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

//    public void save(User user) {
//        webClient.post()
//                .uri("/save")
//                .bodyValue(user)
//                .retrieve()
//                .toBodilessEntity()
//                .block();
//    }

    public void deleteById(Long id) {
        webClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void deleteByUsername(String username) {
        webClient.delete()
                .uri("/{username}", username)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void deleteByEmail(String email) {
        webClient.delete()
                .uri("/{email}", email)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public int count() {
        return Objects.requireNonNull(
                webClient.get()
                        .uri("/count")
                        .retrieve()
                        .bodyToMono(Integer.class)
                        .block()
        );
    }
}
