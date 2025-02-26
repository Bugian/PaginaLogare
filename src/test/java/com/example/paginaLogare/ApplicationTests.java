//package com.example.paginaLogare;
//
//
//import com.example.paginaLogare.user.UserRepository;
//import com.example.paginaLogare.user.UserService;
//import org.apache.catalina.User;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//public class UserServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private UserService userService;
//
//    @Test
//    public void testFindUserById(){
//        User user = new User(1L, "anton", "parola123", "anton@example.com", LocalDateTime.now());
//        when(userRepository.findById((1L)).thenReturn(Optional.of(user)));
//
//        Optional<User> result = userService.findUserById(1L);
//
//        assertTrue(result.isPresent());
//        assertEquals("anton", result.get().getUsername());
//    }
//}