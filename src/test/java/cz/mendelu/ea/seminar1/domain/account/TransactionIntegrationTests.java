package cz.mendelu.ea.seminar1.domain.account;

import cz.mendelu.ea.seminar1.domain.transaction.Transaction;
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
public class TransactionIntegrationTests {
    private static final String BASE_URL = "http://localhost";

    @LocalServerPort
    private int port;

    @BeforeEach
    public void configureRestAssured() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.port = port;
    }
    @Test
    public void testProcessTransactionSuccess() throws Exception {
        Transaction transaction = new Transaction(1L,100, 2L, 1L);
        given()
                .contentType(ContentType.JSON).body(transaction)
                .when()
                .post("/transactions")
                .then()
                .statusCode(201);

        given()
                .when()
                .get("/accounts/1")
                .then()
                .statusCode(200) // Assuming the account exists and is retrieved successfully
                .body("balance", is(200.0F));

    }
    @Test
    public void testProcessTransactionAccountNotFound() throws Exception {
        Transaction transaction = new Transaction(1L,50, 10L, 1L);
        given()
                .contentType(ContentType.JSON).body(transaction)
                .when()
                .post("/transactions")
                .then()
                .statusCode(404); // NOT FOUND for a non-existing account

        transaction = new Transaction(1L,50, 1L, 10L);
        given()
                .contentType(ContentType.JSON).body(transaction)
                .when()
                .post("/transactions")
                .then()
                .statusCode(404); // NOT FOUND for a non-existing account
    }

    @Test
    public void testProcessTransactionInsufficientFunds() throws Exception {
        Transaction transaction = new Transaction(1L,500, 2L, 1L);


        given()
                .contentType(ContentType.JSON).body(transaction)
                .when()
                .post("/transactions")
                .then()
                .statusCode(409); // CONFLICT for insufficient funds
    }
}
