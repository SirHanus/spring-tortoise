package cz.mendelu.ea.domain.acount;

import cz.mendelu.ea.domain.account.Account;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AccountIntegrationTest {

    private final static String BASE_URI = "http://localhost";

    @LocalServerPort
    private int port;

    @BeforeEach
    public void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }

    @Test
    public void testGetAllAccounts() {
        given()
        .when()
                .get("/accounts")
        .then()
                .statusCode(200)
                .body("size()", is(10))
                .body("[0].id", is(0))
                .body("[0].owner", is("Jakub Novák"))
                .body("[0].balance", is(100.0f));
    }

    @Test
    public void testCreateAccount() {
        Account newAccount = new Account(-1L, "Test Testovic", 999.9);
        given()
                .contentType(ContentType.JSON)
                .body(newAccount)
        .when()
                .post("/accounts")
        .then()
                .statusCode(201)
                .body("id", is(10))
                .body("owner", is("Test Testovic"))
                .body("balance", is(999.9f));
    }

    @Test
    public void testCreateAccount_BadRequest() {
        Account newStudent = new Account(null, "", 999.9);

        given()
                .contentType(ContentType.JSON)
                .body(newStudent)
        .when()
                .post("/accounts")
        .then()
                .statusCode(400);
    }

    @Test
    public void testGetAccountById() {
        given()
        .when()
                .get("/accounts/1")
        .then()
                .statusCode(200)
                .body("id", is(1))
                .body("owner", is("Eliška Svobodová"))
                .body("balance", is(200.0f));
    }

    @Test
    public void testGetAccountById_NotFound() {
        given()
                .when()
                .get("/accounts/999")
                .then()
                .statusCode(404);
    }

    @Test
    public void testUpdateAccount() {
        Account updatedAccount = new Account(1L, "Updated User", 999.9);
        given()
                .contentType(ContentType.JSON)
                .body(updatedAccount)
        .when()
                .put("/accounts/1")
        .then()
                .statusCode(202)
                .body("id", is(1))
                .body("owner", is("Updated User"))
                .body("balance", is(999.9f));
    }

    @Test
    public void testUpdateAccount_NotFound() {
        Account updatedAccount = new Account(999L, "Updated User", 999.9);
        given()
                .contentType(ContentType.JSON)
                .body(updatedAccount)
        .when()
                .put("/accounts/999")
        .then()
                .statusCode(404);
    }

    @Test
    public void testUpdateAccount_BadRequest() {
        Account updatedAccount = new Account(1L, "", 999.9);
        given()
                .contentType(ContentType.JSON)
                .body(updatedAccount)
        .when()
                .put("/accounts/1")
        .then()
                .statusCode(400);
    }

    @Test
    public void testDeleteAccount() {
        given()
        .when()
                .delete("/accounts/1")
        .then()
                .statusCode(204);
    }

}