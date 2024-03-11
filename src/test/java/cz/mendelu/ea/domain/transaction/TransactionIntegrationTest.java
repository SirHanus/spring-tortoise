package cz.mendelu.ea.domain.transaction;

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
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TransactionIntegrationTest {

    private final static String BASE_URI = "http://localhost";

    @LocalServerPort
    private int port;

    @BeforeEach
    public void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }


    @Test
    public void testCreateTransaction() {
        Transaction newTransaction = new Transaction(-1L, 100.0, 1L, 2L);

        // process the transaction
        given()
                .contentType(ContentType.JSON)
                .body(newTransaction)
        .when()
                .post("/transactions")
        .then()
                .statusCode(201);

        // check the balances have been updated
        when().get("/accounts/1").then().body("balance", is(100.0f));
        when().get("/accounts/2").then().body("balance", is(400.0f));
    }

    @Test
    public void testCreateTransaction_AccountNotFound() {
        Transaction newTransaction = new Transaction(-1L, 100.0, 1L, 999L);

        // process the transaction
        given()
                .contentType(ContentType.JSON)
                .body(newTransaction)
        .when()
                .post("/transactions")
        .then()
                .statusCode(400);
    }

    @Test
    public void testCreateTransaction_BadRequest() {
        Transaction newTransaction = new Transaction(-1L, -100.0, 1L, 2L);

        // process the transaction
        given()
                .contentType(ContentType.JSON)
                .body(newTransaction)
        .when()
                .post("/transactions")
        .then()
                .statusCode(400);
    }

    @Test
    public void testCreateTransaction_InsufficientResources() {
        Transaction newTransaction = new Transaction(-1L, 1000.0, 1L, 2L);

        // process the transaction
        given()
                .contentType(ContentType.JSON)
                .body(newTransaction)
        .when()
                .post("/transactions")
        .then()
                .statusCode(409);
    }


}