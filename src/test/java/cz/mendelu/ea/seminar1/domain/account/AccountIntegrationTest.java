package cz.mendelu.ea.seminar1.domain.account;

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
    private static final String BASE_URL = "http://localhost";

    @LocalServerPort
    private int port;

    @BeforeEach
    public void configureRestAssured() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.port = port;
    }

    @Test
    public void testGetAllAccounts() {
        given()
                .when()
                .get("/accounts")
                .then()
                .statusCode(200)
                .body("size()", is(4))
                .body("[0].id", is(1))
                .body("[0].owner", is("Mrkev"))
                .body("[0].balance", is(100.0f));
    }

    @Test
    public void testCreateAccount() {
        Account newAccount = new Account(-1L, "Testovator", 800);
        given().contentType(ContentType.JSON).body(newAccount)
                .when()
                .post("/accounts")
                .then()
                .statusCode(201)
                .body("id", is(5))
                .body("owner", is("Testovator"))
                .body("balance", is(800.0f));
    }

    @Test
    public void testCreateAccountBadRequest() {
        Account newAccount = new Account(-1L, "Testovator", -800);
        given().contentType(ContentType.JSON).body(newAccount)
                .when()
                .post("/accounts")
                .then()
                .statusCode(400);

    }

    @Test
    public void testGetAccountByIdCorrect() {
        given().when().get("/accounts/1")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("owner",is("Mrkev"))
                .body("balance", is(100.0F));

    }

    @Test
    public void testGetAccountByIdIncorrect() {
        given().when().get("/accounts/9")
                .then()
                .statusCode(404);


    }
}
