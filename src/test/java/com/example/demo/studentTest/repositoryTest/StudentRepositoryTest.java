package com.example.demo.studentTest.repositoryTest;

import com.example.demo.student.model.Student;
import com.example.demo.student.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void checkIfStudentEmailExists() {
        // given
        Student student = new Student(
                "Rodrigo Valori",
                "rodrigovalori@hotmail.com",
                LocalDate.of(1999, Month.AUGUST, 6)
        );
        underTest.save(student);

        // when
        Optional<Student> expected = underTest.findByEmail(student.getEmail());

        // then
        assertThat(expected.isPresent()).isTrue();
    }

    @Test
    void checkIfStudentEmailDoesNotExists() {
        // given
        Student student = new Student(
                "Rodrigo Valori",
                "rodrigovalori@hotmail.com",
                LocalDate.of(1999, Month.AUGUST, 6)
        );
        underTest.save(student);

        String email = "rod@hotmail.com";

        // when
        Optional<Student> expected = underTest.findByEmail(email);

        // then
        assertThat(expected.isPresent()).isFalse();
    }
}