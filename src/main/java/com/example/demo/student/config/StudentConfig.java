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
                    "Rodrigo",
                    "rodrigovalori@hotmail.com",
                    LocalDate.of(1999, AUGUST, 6)
            );

            Student sophia = new Student(
                    "Sophia",
                    "sophiazuppo@gmail.com",
                    LocalDate.of(2001, MAY, 3)
            );

            repository.saveAll(
                    List.of(rodrigo, sophia)
            );
        };
    }
}
