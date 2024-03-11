package cz.mendelu.ea.domain.user;

import cz.mendelu.ea.domain.transaction.Transaction;
import cz.mendelu.ea.utils.reponse.ArrayResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
    public void testCreateUser() {
        User user = new User(30L, "TomasTest", "TestTomas");

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/users")
                .then()
                .statusCode(201);


    }

    @Test
    public void testSize() {
              given()
                .when()
                .get("/users/size")
                .then()
                .statusCode(200)
                      .body("content", is(10));


    }

    @Test
    public void testCreateUserWithInvalidInput() {
        User invalidUser = new User(31L, "", ""); // Assuming empty username/password is invalid

        given()
                .contentType(ContentType.JSON)
                .body(invalidUser)
                .when()
                .post("/users")
                .then()
                .statusCode(400); // Expecting Bad Request due to invalid input
    }

    @Test
    public void testCreateUserAndVerifyPresence() {
        UserRequest newUser = new UserRequest( "NewUserTest", "PasswordTest");

        given()
                .contentType(ContentType.JSON)
                .body(newUser)
                .when()
                .post("/users")
                .then()
                .statusCode(201);


        Response response = given().when().get("/users/size").then().statusCode(200).extract().response();

        int integer = response.jsonPath().getInt("content")-1;

//
//        given()
//                .pathParam("id", integer)
//                .when()
//                .get("/users/{id}")
//                .then()
//                .statusCode(200)
//                .body("name", is("NewUserTest"))
//                .body("id", equalTo(integer));




//        assertEquals("NewUserTest", lastUser.getUsername());

    }
    @Test
    public void testGetAllUsers() {
        given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("count", is(10))
                .body("items[0].id", is(0)) // Assuming you want to check the ID as well
                .body("items[0].name", is("Patrick"))
                .body("items[0].username", is("patric2023"))
                .body("items[0].average", is("-1"))
                .body("items[1].accountIDs[0]", is(1)); // Checks the first accountID of the first item
    }
    @Test
    public void testGetUserById() {
        given()
                .when()
                .get("/users/2")
                .then()
                .statusCode(200)
                .body("content.id", is(2)) // Assuming you want to check the ID as well
                .body("content.name", is("Alexander"))
                .body("content.username", is("alexTheGreat"))
                .body("content.average", is("90.00"))
                .body("content.accountIDs[0]", is(3)); // Checks the first accountID of the first item
    }

    @Test
    public void testUserAverageTransaction() {
        given()
                .when()
                .get("/users/4")
                .then()
                .statusCode(200)
                .body("content.id", is(4)) // Assuming you want to check the ID as well
                .body("content.average", is("250.00"));
    }


}