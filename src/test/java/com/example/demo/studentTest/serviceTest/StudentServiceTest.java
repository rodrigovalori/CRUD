package com.example.demo.studentTest.serviceTest;

import com.example.demo.student.model.Student;
import com.example.demo.student.repository.StudentRepository;
import com.example.demo.student.service.StudentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

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
    void getStudent() {
        // when
        underTest.getStudents();

        // then
        verify(studentRepository).findAll();
    }

    @Test
    void addNewStudent() {
        // given
        Student student = new Student(
                "Rodrigo",
                "rodrigovalori@hotmail.com",
                LocalDate.of(1999, Month.AUGUST, 6)
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
                "Rodrigo",
                "rodrigovalori@hotmail.com",
                LocalDate.of(1999, Month.AUGUST, 6)
        );

        studentRepository.save(student);

        given(studentRepository.findByEmail(student.getEmail())).willReturn(Optional.of(student));
        given(studentRepository.findById(student.getId())).willReturn(Optional.of(student));

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
                "Rodrigo",
                "rodrigovalori@hotmail.com",
                LocalDate.of(1999, Month.AUGUST, 6)
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
                "Rodrigo",
                "rodrigovalori@hotmail.com",
                LocalDate.of(1999, Month.AUGUST, 6));

        studentRepository.save(student);

        // when
        given(studentRepository.existsById(student.getId())).willReturn(false);

        // then
        assertThatThrownBy(() -> underTest.deleteStudent(student.getId()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Student with id " + student.getId() + " does not exist!");

        assertThatThrownBy(() -> underTest.updateStudent(student.getId(), student.getName(), student.getEmail()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Student with id " + student.getId() + " does not exist!");
    }

    @Test
    void updateStudent() {
        // given
        Student student = new Student("Rodrigo",
                "rodrigovalori@hotmail.com",
                LocalDate.of(1999, Month.AUGUST, 6));

        studentRepository.save(student);

        String name = "Rod";
        String email = "rod@hotmail.com";

        given(studentRepository.findById(student.getId())).willReturn(Optional.of(student));

        // when
        underTest.updateStudent(student.getId(), name, email);

        // then
        assertThat(student.getName()).isEqualTo(name);
        assertThat(student.getEmail()).isEqualTo(email);
    }
}