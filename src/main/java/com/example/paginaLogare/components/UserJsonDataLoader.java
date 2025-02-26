//package com.example.paginaLogare.components;
//
//import com.example.paginaLogare.JdbcClient.JdbcClientUserRepository;
//import com.example.paginaLogare.user.Users;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.asm.TypeReference;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.io.InputStream;
//import java.io.IOException;
//
//@Component
//public class UserJsonDataLoader implements CommandLineRunner {
//
//    private static final Logger log = LoggerFactory.getLogger(UserJsonDataLoader.class);
//    private final JdbcClientUserRepository userRepository;
//    private final ObjectMapper objectMapper;
//
//    public UserJsonDataLoader(JdbcClientUserRepository userRepository, ObjectMapper objectMapper) {
//        this.userRepository = userRepository;
//        this.objectMapper = objectMapper;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        if (userRepository.count() == 0) {
//            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/users.json")) {
//                log.info("Loading users.json");
//                Users allUsers = objectMapper.readValue(inputStream, Users.class);
//                log.info("Reading {} users from JSON data and saving to a database.", allUsers.users().size());
//                userRepository.saveAll(allUsers.users());
//            } catch (IOException e) {
//                throw new RuntimeException("Error reading users from JSON data", e);
//            }
//        } else {
//            log.info("Not loading Runs from JSON data because the collection contains data.");
//        }
//    }
//}
