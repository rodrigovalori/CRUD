package com.studentTest.controllerTests;

import com.student.model.Student;
import com.student.service.StudentService;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static java.time.Month.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class StudentControllerTest {

    @Mock
    private Student student;
    private List<Student> studentList;

    @PostConstruct
    void setUp() throws Exception {
        student = new Student();
        student.setId(1L);
        student.setName("Steve Rogers");
        student.setEmail("captainAmerica@gmail.com");
        student.setDob(LocalDate.of(1918, JULY, 4));

        studentList = new ArrayList<>();
        studentList.add(new Student ("Peter Parker",
                "spiderMan@gmail.com",
                LocalDate.of(2001, AUGUST, 10)));
        studentList.add(new Student ("Tony Stark",
                "ironMan@gmail.com",
                LocalDate.of(1970, MAY, 29)));

        baseURI = "http://localhost:" + port + "/";
    }

    @LocalServerPort
    private int port;

    @MockBean
    private StudentService studentService;

    @Test
    public void anyMethodWhenPathIsIncorrectShouldReturnStatus404() {

        Response response = given()
                .port(port)
                .auth()
                .basic("admin","password")
                .contentType(ContentType.JSON)
                .when()
                .get(baseURI + "api/v1/studentstudent")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(404, response.statusCode());
    }

    @Test
    public void getAllStudentsWhenUsernameAndPasswordAreCorrectShouldReturnStatus200() {

        Mockito.when(studentService.getAllStudents()).thenReturn(studentList);

        Response response = given()
                .port(port)
                .auth()
                .basic("admin","password")
                .contentType(ContentType.JSON)
                .when()
                .get(baseURI + "api/v1/student/")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void getAllStudentsWhenUsernameAndPasswordAreIncorrectShouldReturnStatus401() {

        Mockito.when(studentService.getAllStudents()).thenReturn(studentList);

        Response response = given()
                .port(port)
                .auth()
                .basic("","")
                .contentType(ContentType.JSON)
                .when()
                .get(baseURI + "api/v1/student/")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(401, response.statusCode());
    }

    @Test
    public void getStudentByIdWhenUsernameAndPasswordAreCorrectShouldReturnStatus200() {

        Mockito.when(studentService.getStudentById(student.getId())).thenReturn(java.util.Optional.of(student));

        Response response = given()
                .port(port)
                .auth()
                .basic("admin","password")
                .contentType(ContentType.JSON)
                .when()
                .get(baseURI + "api/v1/student/" + student.getId())
                .then()
                .extract()
                .response();

        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void getStudentByIdWhenUsernameAndPasswordAreIncorrectShouldReturnStatus401() {

        Mockito.when(studentService.getStudentById(student.getId())).thenReturn(java.util.Optional.of(student));

        Response response = given()
                .port(port)
                .auth()
                .basic("","")
                .contentType(ContentType.JSON)
                .when()
                .get(baseURI + "api/v1/student/" + student.getId())
                .then()
                .extract()
                .response();

        Assertions.assertEquals(401, response.statusCode());
    }

    @Test
    public void addNewStudentWhenUsernameAndPasswordAreCorrectShouldReturnStatus200() throws JSONException {

        JSONObject requestParams = new JSONObject();

        requestParams.put("id", student.getId());
        requestParams.put("name", student.getName());
        requestParams.put("email", student.getEmail());
        requestParams.put("dob", student.getDob());

        Mockito.when(studentService.addNewStudent(student)).thenReturn(student);

        Response response = given()
                .port(port)
                .auth()
                .basic("admin","password")
                .header("Content-Type","application/json")
                .body(requestParams.toString())
                .when()
                .post("api/v1/student/")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Student " + student.getName() + " added!", response.getBody().asString());
    }

    @Test
    public void addNewStudentWhenUsernameAndPasswordAreIncorrectShouldReturnStatus401() throws JSONException {

        JSONObject requestParams = new JSONObject();

        requestParams.put("id", student.getId());
        requestParams.put("name", student.getName());
        requestParams.put("email", student.getEmail());
        requestParams.put("dob", student.getDob());

        Mockito.when(studentService.addNewStudent(student)).thenReturn(student);

        Response response = given()
                .port(port)
                .auth()
                .basic("","")
                .header("Content-Type","application/json")
                .body(requestParams.toString())
                .when()
                .post("api/v1/student/")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(401, response.statusCode());
        Assertions.assertEquals("", response.getBody().asString());
    }

    @Test
    public void deleteStudentWhenUsernameAndPasswordAreCorrectShouldReturnStatus200() {

        Mockito.when(studentService.deleteStudent(student.getId())).thenReturn(student.getId());

        Response response = given()
                .port(port)
                .auth()
                .basic("admin","password")
                .header("Content-Type","application/json")
                .when()
                .delete("api/v1/student/" + student.getId())
                .then()
                .extract()
                .response();

        System.out.println(response.getBody().asString());
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Student with id " + student.getId() + " deleted!", response.getBody().asString());
    }

    @Test
    public void deleteStudentWhenUsernameAndPasswordAreIncorrectShouldReturnStatus401() {

        Mockito.when(studentService.deleteStudent(student.getId())).thenReturn(student.getId());

        Response response = given()
                .port(port)
                .auth()
                .basic("","")
                .header("Content-Type","application/json")
                .when()
                .delete("api/v1/student/1")
                .then()
                .extract()
                .response();

        System.out.println(response.getBody().asString());
        Assertions.assertEquals(401, response.statusCode());
    }

    @Test
    public void updateStudentWhenUsernameAndPasswordAreCorrectShouldReturnStatus200() {

        Mockito.when(studentService.updateStudent(student.getId(), student.getName(), student.getEmail())).thenReturn(studentList);

        Response response = given()
                .port(port)
                .auth()
                .basic("admin","password")
                .header("Content-Type","application/json")
                .when()
                .put("api/v1/student/" + student.getId() + "?name=" + student.getName() + "?email=" + student.getEmail())
                .then()
                .extract()
                .response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Student with id " + student.getId() + " updated!", response.getBody().asString());
    }

    @Test
    public void updateStudentWhenUsernameAndPasswordAreIncorrectShouldReturnStatus401() {

        Mockito.when(studentService.updateStudent(student.getId(), student.getName(), student.getEmail())).thenReturn(studentList);

        Response response = given()
                .port(port)
                .auth()
                .basic("","")
                .header("Content-Type","application/json")
                .when()
                .put("api/v1/student/" + student.getId() + "?name=" + student.getName() + "?email=" + student.getEmail())
                .then()
                .extract()
                .response();

        Assertions.assertEquals(401, response.statusCode());
        Assertions.assertEquals("", response.getBody().asString());
    }
}