package cz.mendelu.ea.domain.transaction;

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

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.is;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/test-data/cleanup.sql")
@Sql("/test-data/base-data.sql")
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
        var newTransaction = new TransactionRequest(100.0, 1L, 2L);

        // process the transaction
        given()
                .contentType(ContentType.JSON)
                .body(newTransaction)
        .when()
                .post("/transactions")
        .then()
                .statusCode(201);

        // check the balances have been updated
        when().get("/accounts/1").then()
                .body("content.balance", is(0.0f))
                .body("content.transactionCount", is(1));
        when().get("/accounts/2").then()
                .body("content.balance", is(300.0f))
                .body("content.transactionCount", is(1));
    }

    @Test
    public void testCreateTransaction_AccountNotFound() {
        var newTransaction = new TransactionRequest(100.0, 1L, 999L);

        // process the transaction
        given()
                .contentType(ContentType.JSON)
                .body(newTransaction)
        .when()
                .post("/transactions")
        .then()
                .statusCode(404);
    }

    @Test
    public void testCreateTransaction_BadRequest() {
        var newTransaction = new TransactionRequest(-100.0, 1L, 2L);

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
        var newTransaction = new TransactionRequest(1000.0, 1L, 2L);

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