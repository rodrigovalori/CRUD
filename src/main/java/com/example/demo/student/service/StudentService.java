package com.example.demo.student.service;

import com.example.demo.student.model.Student;
import com.example.demo.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> getStudentById(Long studentId) {
        Optional<Student> existsById = studentRepository.findById(studentId);
        if(!existsById.isPresent()) {
            throw new IllegalStateException("Student with id " + studentId + " does not exist!");
        }
        return studentRepository.findStudentById(studentId);
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentEmailExists = studentRepository
                .findStudentByEmail(student.getEmail());
        if (studentEmailExists.isPresent()) {
            throw new IllegalStateException("Email " + student.getEmail() + " taken!");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new IllegalStateException(
                    "Student with id " + studentId + " does not exist!");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId,
                              String name,
                              String email) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "Student with id " + studentId + " does not exist!"));

        if (name != null &&
                name.length() > 0) {
            student.setName(name);
        }

        if (email != null &&
                email.length() > 0) {
            Optional<Student> existsByEmail = studentRepository.findStudentByEmail(email);
            if (existsByEmail.isPresent()) {
                throw new IllegalStateException("Email " + email + " taken!");
            }
            student.setEmail(email);
        }
    }
}