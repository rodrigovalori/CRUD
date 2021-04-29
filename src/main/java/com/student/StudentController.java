package com.student;

import com.student.model.Student;
import com.student.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/student")
@Slf4j
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudents(){
        log.info("getAllStudents endpoint was called");
        return studentService.getAllStudents();
    }

    @GetMapping(path = "{studentId}")
    public Optional<Student> getStudentById(
            @PathVariable("studentId") Long studentId) {
        log.info("getStudentById endpoint was called");
        return studentService.getStudentById(studentId);
    }

    @PostMapping
    public String addNewStudent(@RequestBody Student student) {
        log.info("addNewStudent endpoint was called");
        studentService.addNewStudent(student);
        return ("Student " + student.getName() + " added!");
    }

    @DeleteMapping(path = "{studentId}")
    public String deleteStudent(
            @PathVariable("studentId") Long studentId) {
        log.info("deleteStudent endpoint was called");
        studentService.deleteStudent(studentId);
        return ("Student with id " + studentId + " deleted!");
    }

    @PutMapping(path = "{studentId}")
    public String updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        log.info("updateStudent endpoint was called");
        studentService.updateStudent(studentId, name, email);
        return ("Student with id " + studentId + " updated!");
    }
}
