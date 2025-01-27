package com.example.paginaLogare.useri;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();
    Optional<User> findById(Long id);
    void create(User user);
    void update(User user, Long id);
    int count();
    void save(User user);
//    User saveAll(List<User> users);
    User findByEmail(String email);
    User findByUsername(String username);
    void deleteById(Long id);
    void deleteByUsername(String username);
    void deleteByEmail(String email);


}
