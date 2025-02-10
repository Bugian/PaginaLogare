package com.example.paginaLogare.webClient;

import com.example.paginaLogare.useri.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;

public interface UserHttpClient {

    @GetExchange
    List<User> findAll();

    @GetExchange("/{id}")
    User findById(@PathVariable Long id);

    @GetExchange("/email/{email}")
    User findByEmail(@PathVariable String email);

    @GetExchange("/username/{username}")
    User findByUsername(@PathVariable String username);

    @PostExchange
    void create(@RequestBody User user);

//    @PostExchange("/save")
//    void save(User user);

    @PutExchange("/{id}")
    void update(@PathVariable Long id, @RequestBody User user);

    @DeleteExchange("/{id}")
    void deleteById(@PathVariable Long id);

    @DeleteExchange("/email/{email}")
    void deleteByEmail(@PathVariable String email);

    @DeleteExchange("/username/{username}")
    void deleteByUsername(@PathVariable String username);

    @GetExchange("/count")
    int count();


}
