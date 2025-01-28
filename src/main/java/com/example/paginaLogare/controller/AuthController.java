//package com.example.paginaLogare.controller;
//
//import com.example.paginaLogare.useri.UserRepository;
//import com.example.paginaLogare.useri.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api")
//public class AuthController {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(loginRequest.getEmail()));
//
//        if (user.isPresent() && user.get().getPassword().equals(loginRequest.getPassword())) {
//            return ResponseEntity.ok("Login succesful");
//        } else {
//            return ResponseEntity.status(401).body("Invalid mail or password");
//        }
//    }
//
//
//    class LoginRequest {
//        private String email;
//        private String password;
//
//        public String getEmail() {
//            return email;
//        }
//
//        public void setEmail(String email) {
//            this.email = email;
//        }
//
//        public String getPassword() {
//            return password;
//        }
//
//        public void setPassword(String password) {
//            this.password = password;
//        }
//    }
//}
