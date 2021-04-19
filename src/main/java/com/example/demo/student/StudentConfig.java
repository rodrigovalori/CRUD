package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static java.time.Month.*;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            StudentRepository repository) {
        return args -> {
            Student rodrigo = new Student(
                    "Rodrigo",
                    "rodrigovalori@hotmail.com",
                    LocalDate.of(1999, AUGUST, 06)
            );

            Student sophia = new Student(
                    "Sophia",
                    "sophiazuppo@gmail.com",
                    LocalDate.of(2001, MAY, 03)
            );

            repository.saveAll(
                    List.of(rodrigo, sophia)
            );
        };
    }
}