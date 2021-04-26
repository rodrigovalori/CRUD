package com.example.demo.student.config;

import com.example.demo.student.model.Student;
import com.example.demo.student.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.*;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            StudentRepository repository) {
        return args -> {
            Student rodrigo = new Student(
                    "Peter Parker",
                    "spiderMan@gmail.com",
                    LocalDate.of(2001, AUGUST, 10)
            );

            Student sophia = new Student(
                    "Tony Stark",
                    "ironMan@gmail.com",
                    LocalDate.of(1970, MAY, 29)
            );

            repository.saveAll(
                    List.of(rodrigo, sophia)
            );
        };
    }
}
