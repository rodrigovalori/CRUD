package com.student.model;

import com.student.repository.StudentRepository;
import com.student.service.StudentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Period;

import static java.time.Month.AUGUST;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class StudentTest {

    @Mock
    private StudentRepository studentRepository;

    @Test
    void getAge() {
        Student student = new Student(
                "Peter Parker",
                "spiderMan@gmail.com",
                LocalDate.of(2001, AUGUST, 10)
        );

        Assertions.assertEquals(Period.between(student.getDob(), LocalDate.now()).getYears(), student.getAge());
    }
}
