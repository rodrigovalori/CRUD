package com.student.repository;

import com.student.model.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static java.time.Month.AUGUST;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class StudentRepositoryIntegrationTest {

    @Autowired
    private StudentRepository studentRepository;

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    void checkIfStudentEmailExists() {
        // given
        Student student = new Student(
                "Peter Parker",
                "spiderMan@gmail.com",
                LocalDate.of(2001, AUGUST, 10)
        );

        studentRepository.save(student);

        // when
        Optional<Student> expected = studentRepository.findByEmail(student.getEmail());

        // then
        assertThat(expected.isPresent()).isTrue();
    }

    @Test
    void checkIfStudentEmailDoesNotExists() {
        // given
        Student student = new Student(
                "Peter Parker",
                "spiderMan@gmail.com",
                LocalDate.of(2001, AUGUST, 10)
        );

        studentRepository.save(student);

        String email = "peter@gmail.com";

        // when
        Optional<Student> expected = studentRepository.findByEmail(email);

        // then
        assertThat(expected.isPresent()).isFalse();
    }
}