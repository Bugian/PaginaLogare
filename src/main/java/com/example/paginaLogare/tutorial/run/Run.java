//package com.example.paginaLogare.run;
//
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Positive;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.annotation.Version;
//
//import java.time.LocalDateTime;
//
//public record Run(
//        @Id
//        Integer id,
//        @NotEmpty(message = "Title cannot be empty")
//        String title,
//        LocalDateTime startedOn,
//        LocalDateTime completedOn,
//        @Positive(message = "Miles must be positive")
//        Integer miles,
//        Location location
//) {
//    public Run {
//        if (!completedOn.isAfter(startedOn)) {
//            throw new IllegalArgumentException("Completed date-time cannot be before started date-time");
//        }
//    }
//
//
//}
