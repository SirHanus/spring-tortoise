package cz.mendelu.ea.domain.user;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class UserIntegrationTest {

    private final static String BASE_URI = "http://localhost";

    @LocalServerPort
    private int port;

    @BeforeEach
    public void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }

    @Test
    public void testGetUsers() {
        when()
                .get("/users")
        .then()
                .statusCode(200)
                .body("items.size()", is(2))
                .body("items.name", containsInAnyOrder("Ivo", "Marie"));
    }

    @Test
    public void testGetUserById() {
        when()
                .get("/users/1")
        .then()
                .statusCode(200)
                .body("content.name", is("Ivo"))
                .body("content.username", is("ivo"))
                .body("content.accountIds", containsInAnyOrder(2))
                .body("content.avgBalance", is(200.0f));
    }

    @Test
    public void testGetUserById_NotFound() {
        when()
                .get("/users/999")
        .then()
                .statusCode(404);
    }

    @Test
    public void testCreateUser() {
        var userRequest = new UserRequest("John Doe", "johndoe", List.of(1L, 2L, 999L));

        given()
                .contentType(ContentType.JSON)
                .body(userRequest)
        .when()
                .post("/users")
        .then()
                .statusCode(201)
                .body("content.id", is(3));

        when()
                .get("/users/3")
        .then()
                .statusCode(200)
                .body("content.name", is("John Doe"))
                .body("content.username", is("johndoe"))
                .body("content.accountIds", containsInAnyOrder(1, 2));
    }

    @Test
    public void testCreateUser_BadRequest() {
        var userRequest = new UserRequest("John Doe", "jo", List.of(1L, 2L, 999L)); // too short username

        given()
                .contentType(ContentType.JSON)
                .body(userRequest)
        .when()
                .post("/users")
        .then()
                .statusCode(400);
    }

}