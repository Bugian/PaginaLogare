package com.example.paginaLogare;

import com.example.paginaLogare.useri.User;
import com.example.paginaLogare.useri.UserHttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.List;

@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    UserHttpClient userHttpClient() {
        RestClient restClient = RestClient.create("http://localhost:8080/api/users/");
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
        return factory.createClient(UserHttpClient.class);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserHttpClient client) {
        return args -> {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            //1. Find all users//
            log.info("Fetching all users...");
            List<User> users = client.findAll();
            users.forEach(user -> log.info("User: {}", user));

            // 2. Create a new user
            log.info("Creating a new user...");
            User newUser = new User(null, "newuser", "password", "newuser@example.com", null);
            client.create(newUser);
            log.info("User created: {}", newUser);

            // 2. Create a new user
            log.info("Creating a new user...");
            User newUser2 = new User(null, "newuser2", "password", "newuser2@example.com", null);
            client.create(newUser2);
            log.info("User created: {}", newUser2);

            // 2. Create a new user
            log.info("Creating a new user...");
            User newUser3 = new User(null, "newuser3", "password", "newuser3@example.com", null);
            client.create(newUser3);
            log.info("User created: {}", newUser3);

            //1. Find all users//
            log.info("Fetching all users...");
            List<User> usersFind = client.findAll();
            users.forEach(user -> log.info("User: {}", usersFind));


            // 3. Find user by ID
            log.info("Fetching user by ID: 1");
            User userById = client.findById(1L);
            log.info("User by ID: {}", userById);

            // 3. Find user by email
            log.info("Fetching user by email: newuser2@example.com");
            User userByEmail = client.findByEmail("newuser2@example.com");
            log.info("User by email: {}", userByEmail);

            // 3. Find user by ID
            log.info("Fetching user by username: newuser3");
            User userByUsername = client.findByUsername("newuser3");
            log.info("User by username: {}", userByUsername);

            // 4. Update an existing user
            log.info("Updating user with ID: 1");
            User updatedUser = new User(1L, "updateduser", "newpassword", "updateduser@example.com", null);
            client.update(1L, updatedUser);
            log.info("User updated: {}", updatedUser);

            // 5. Delete user by ID
            log.info("Deleting user with ID: 1");
            client.deleteById(1L);
            log.info("User with ID: 1 deleted");

            // 6. Delete user by email
            log.info("Deleting user with email: updateduser@example.com");
            client.deleteByEmail("newuser2@example.com");
            log.info("User with email: newuser2@example.com deleted");

            // 7. Delete user by username
            log.info("Deleting user with username: newuser3");
            client.deleteByUsername("newuser3");
            log.info("User with username: newuser3 deleted");

            // 8. Count users
            log.info("Counting users...");
            int userCount = client.count();
            log.info("Total users: {}", userCount);
        };
        }
    }

