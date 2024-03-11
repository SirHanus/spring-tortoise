package cz.mendelu.ea.domain.acount;

import cz.mendelu.ea.domain.account.Account;
import cz.mendelu.ea.domain.account.AccountRequest;
import cz.mendelu.ea.domain.user.User;
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
                .body("count", is(10))
                .body("items[0].id", is(1))
                .body("items[0].owner.name", is("Patrick"))
                .body("items[0].balance", is(100.0f));
    }

    @Test
    public void testCreateAccount() {
        AccountRequest newAccount = new AccountRequest(1L);
        given()
                .contentType(ContentType.JSON)
                .body(newAccount)
        .when()
                .post("/accounts")
        .then()
                .statusCode(201)
                .body("id", is(10))
                .body("owner.name", is("Test Testovic"))
                .body("balance", is(999.9f));
    }

    @Test
    public void testCreateAccount_BadRequest() {
        AccountRequest newAccount = new AccountRequest(-1L);

        given()
                .contentType(ContentType.JSON)
                .body(newAccount)
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
                .body("content.id", is(1))
                .body("content.owner", is(0))
                .body("content.balance", is(100.0f));
    }

    @Test
    public void testGetAccountById_NotFound() {
        given()
                .when()
                .get("/accounts/999")
                .then()
                .statusCode(404);
    }

//    @Test
//    public void testUpdateAccount() {
//        AccountRequest newAccount = new AccountRequest(1);
//        given()
//                .contentType(ContentType.JSON)
//                .body(newAccount)
//        .when()
//                .put("/accounts/1")
//        .then()
//                .statusCode(202)
//                .body("id", is(1))
//                .body("owner", is("Updated User"))
//                .body("balance", is(999.9f));
//    }

//    @Test
//    public void testUpdateAccount_NotFound() {
//        AccountRequest newAccount = new AccountRequest(99);
//        given()
//                .contentType(ContentType.JSON)
//                .body(newAccount)
//        .when()
//                .put("/accounts/999")
//        .then()
//                .statusCode(404);
//    }

//    @Test
//    public void testUpdateAccount_BadRequest() {
//        AccountRequest updatedAccount = new AccountRequest(99);
//        given()
//                .contentType(ContentType.JSON)
//                .body(updatedAccount)
//        .when()
//                .put("/accounts/1")
//        .then()
//                .statusCode(400);
//    }

    @Test
    public void testDeleteAccount() {
        given()
        .when()
                .delete("/accounts/1")
        .then()
                .statusCode(204);
    }

}