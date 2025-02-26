package com.example.paginaLogare.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Metode de bază oferite de JpaRepository (nu trebuie declarate explicit):
    // - save (pentru creare și actualizare)
    // - findById
    // - findAll
    // - deleteById
    // - count

    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    void deleteByUsername(String username);
    void deleteByEmail(String email);
}
