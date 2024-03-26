package cz.mendelu.ea.domain.user;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/test-data/cleanup.sql")
@Sql("/test-data/base-data.sql")
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
                .body("items.name", containsInAnyOrder("Ivo", "Marie"))
                .body("items.username", containsInAnyOrder("ivo", "mar777"))
                .body("items[0].accountIds", containsInAnyOrder(1, 2))
                .body("items[1].accountIds", containsInAnyOrder(2));
    }

    @Test
    public void testGetUserById() {
        when()
                .get("/users/1")
        .then()
                .statusCode(200)
                .body("content.name", is("Ivo"))
                .body("content.username", is("ivo"))
                .body("content.accountIds", containsInAnyOrder(1,2))
                .body("content.avgBalance", is(150.0f));
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

        int id = given()
                .contentType(ContentType.JSON)
                .body(userRequest)
        .when()
                .post("/users")
        .then()
                .statusCode(201)
        .extract()
                .path("content.id");

        when()
                .get("/users/" + id)
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

    @Test
    public void testDeleteUser() {
        when()
                .delete("/users/1")
        .then()
                .statusCode(204);

        // user is deleted
        when().get("/users/1").then().statusCode(404);

        // also his owned accounts are deleted
        when().get("/accounts/1").then().statusCode(404);

        // but not the account he is just a user of
        when().get("/accounts/2").then().statusCode(200);

        // transactions attached to the deleted account have nulls instead of the deleted account
        when().get("/transactions/fffd85db-55c5-4620-b7eb-73191a43533e").then()
                .statusCode(200)
                .body("content.sourceAccountId", is(nullValue()))
                .body("content.targetAccountId", is(2));
        when().get("/transactions/5fdba127-ab33-4881-bcf8-096e210fe7c9").then()
                .statusCode(200)
                .body("content.sourceAccountId", is(2))
                .body("content.targetAccountId", is(nullValue()));
    }

}