package com.example.demo.studentTest.basicAuthTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)

    public class BasicAuthTest {

        @Test
        public void BasicAuthenticationTest_whenStatusCode_200(){
            given().auth()
                    .basic("admin", "password")
                    .when()
                    .get("http://localhost:8080/api/v1/student")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value());
        }

        @Test
        public void BasicAuthenticationTest_whenStatusCode_401(){
            given().auth()
                    .basic("", "")
                    .when()
                    .get("http://localhost:8080/api/v1/student")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.UNAUTHORIZED.value());
        }

        @Test
        public void BasicAuthenticationTest_whenStatusCode_404(){
            given().auth()
                    .basic("admin", "password")
                    .when()
                    .get("http://localhost:8080/api/v1/studentstudent")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.NOT_FOUND.value());
        }
    }