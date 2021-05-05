package com.student.service;

import com.student.model.Student;
import com.student.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static java.time.Month.AUGUST;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceUnitTest {

    @Mock
    private StudentRepository studentRepository;
    private AutoCloseable autoCloseable;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new StudentService(studentRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllStudents() {
        // when
        underTest.getAllStudents();

        // then
        verify(studentRepository).findAll();
    }

    @Test
    void getStudentById() {
        // given
        Student student = new Student(
                "Peter Parker",
                "spiderMan@gmail.com",
                LocalDate.of(2001, AUGUST, 10)
        );

        given(studentRepository.existsById(student.getId())).willReturn(true);

        underTest.getStudentById(student.getId());

        verify(studentRepository).existsById(student.getId());
    }

    @Test
    void addNewStudent() {
        // given
        Student student = new Student(
                "Peter Parker",
                "spiderMan@gmail.com",
                LocalDate.of(2001, AUGUST, 10)
        );

        // when
        underTest.addNewStudent(student);

        // then
        ArgumentCaptor<Student> studentArgumentCaptor =
                ArgumentCaptor.forClass(Student.class);

        verify(studentRepository)
                .save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();

        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void exceptionWhenEmailIsTaken() {
        // given
        Student student = new Student(
                "Peter Parker",
                "spiderMan@gmail.com",
                LocalDate.of(2001, AUGUST, 10)
        );

        given(studentRepository.findByEmail(student.getEmail())).willReturn(of(student));
        given(studentRepository.findById(student.getId())).willReturn(of(student));

        // when
        // then
        assertThatThrownBy(() -> underTest.addNewStudent(student))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Email " + student.getEmail() + " taken!");

        assertThatThrownBy(() -> underTest.updateStudent(student.getId(), student.getName(), student.getEmail()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Email " + student.getEmail() + " taken!");
    }

    @Test
    void deleteStudent() {
        // given
        Student student = new Student(
                "Peter Parker",
                "spiderMan@gmail.com",
                LocalDate.of(2001, AUGUST, 10)
        );

        given(studentRepository.existsById(student.getId())).willReturn(true);

        // when
        underTest.deleteStudent(student.getId());

        // then
        verify(studentRepository).deleteById(student.getId());
    }

    @Test
    void exceptionWhenIdNotFound() {
        // given
        Student student = new Student(
                "Peter Parker",
                "spiderMan@gmail.com",
                LocalDate.of(2001, AUGUST, 10)
        );

        // when
        given(studentRepository.existsById(student.getId())).willReturn(false);

        // then
        assertThatThrownBy(() -> underTest.deleteStudent(student.getId()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Student with id " + student.getId() + " does not exists!");

        assertThatThrownBy(() -> underTest.updateStudent(student.getId(), student.getName(), student.getEmail()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Student with id " + student.getId() + " does not exists!");

        assertThatThrownBy(() -> underTest.getStudentById(student.getId()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Student with id " + student.getId() + " does not exists!");
    }

    @Test
    void updateStudent() {
        // given
        Student student = new Student(
                "Peter Parker",
                "spiderMan@gmail.com",
                LocalDate.of(2001, AUGUST, 10)
        );

        String name = "Peter";
        String email = "peter@gmail.com";

        given(studentRepository.findById(student.getId())).willReturn(of(student));

        // when
        underTest.updateStudent(student.getId(), name, email);

        // then
        assertThat(student.getName()).isEqualTo(name);
        assertThat(student.getEmail()).isEqualTo(email);
    }
}