package com.example.demo.student.model;

import com.example.demo.student.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.AUGUST;
import static java.time.Month.MAY;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            StudentRepository repository) {
        return args -> {
            Student peterParker = new Student(
                    "Peter Parker",
                    "spiderMan@gmail.com",
                    LocalDate.of(2001, AUGUST, 10)
            );

            Student tonyStark = new Student(
                    "Tony Stark",
                    "ironMan@gmail.com",
                    LocalDate.of(1970, MAY, 29)
            );
            repository.saveAll(
                    List.of(peterParker, tonyStark)
            );
        };
    }
}