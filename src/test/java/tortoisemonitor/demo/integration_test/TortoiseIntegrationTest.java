package tortoisemonitor.demo.integration_test;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import tortoisemonitor.demo.domain.tortoise.TortoiseRequest;
import tortoisemonitor.demo.domain.tortoise.TortoiseSpecies;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Sql("/test-data/cleanup.sql")
@Sql("/test-data/base-data.sql")
public class TortoiseIntegrationTest extends BaseIntegrationTest {

    @Test
    public void testGetAllTortoises() {
        String accessToken = obtainAccessToken(); // Use the correct username and password

        given()
                .auth().oauth2(accessToken)
                .when()
                .get("/tortoises")
                .then()
                .statusCode(200)
                .body("size()", is(3))
                .body("name", containsInAnyOrder("Tortoise1", "Tortoise2", "Tortoise3"));
    }

    @Test
    public void testCreateTortoiseInvalidRequest() {
        String accessToken = obtainAccessToken(); // Use the correct username and password

        var newTortoise = new TortoiseRequest("Tortoise4",
                TortoiseSpecies.HERMANN, 5,
                "Healthy", "11111111-1111-1111-1111-111111111111");
        given()
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body(newTortoise)
                .when()
                .post("/tortoises")
                .then()
                .statusCode(400);
    }

    @Test
    public void testCreateTortoise() {
        String accessToken = obtainAccessToken(); // Use the correct username and password

        var newTortoise = new TortoiseRequest("Tortoise4",
                TortoiseSpecies.HERMANN, 5,
                "Healthy", "Habitat1");
        String stringId = given()
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body(newTortoise)
                .when()
                .post("/tortoises")
                .then()
                .statusCode(201)
                .extract()
                .path("uuid");

        UUID id = UUID.fromString(stringId);

        given()
                .auth().oauth2(accessToken)
                .when()
                .get("/tortoises/" + id)
                .then()
                .statusCode(200)
                .body("uuid", is(id.toString()))
                .body("name", is("Tortoise4"))
                .body("species", is("HERMANN"))
                .body("age", is(5))
                .body("healthStatus", is("Healthy"))
                .body("habitatName", is("Habitat1"));
    }

    @Test
    public void testGetTortoiseById() {
        String accessToken = obtainAccessToken(); // Use the correct username and password

        UUID id = UUID.fromString("33333333-3333-3333-3333-333333333333"); // Use a valid UUID from setup data

        given()
                .auth().oauth2(accessToken)
                .pathParam("id", id)
                .when()
                .get("/tortoises/{id}")
                .then()
                .statusCode(200)
                .body("uuid", is(id.toString()))
                .body("name", is("Tortoise1"))
                .body("species", is("HERMANN"))
                .body("age", is(5))
                .body("healthStatus", is("Healthy"))
                .body("habitatName", is("Habitat1"));
    }

    @Test
    public void testGetTortoiseByInvalidId() {
        String accessToken = obtainAccessToken(); // Use the correct username and password

        UUID invalidId = UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff"); // Non-existent ID

        given()
                .auth().oauth2(accessToken)
                .pathParam("id", invalidId)
                .when()
                .get("/tortoises/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testUpdateTortoise() {
        String accessToken = obtainAccessToken(); // Use the correct username and password

        UUID id = UUID.fromString("33333333-3333-3333-3333-333333333333"); // Assume this ID exists
        var updatedTortoise = new TortoiseRequest("UpdatedTortoise1",
                TortoiseSpecies.HERMANN, 6,
                "Healthy", "Habitat1");

        given()
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(updatedTortoise)
                .when()
                .put("/tortoises/{id}")
                .then()
                .statusCode(200)
                .body("name", is("UpdatedTortoise1"))
                .body("species", is("HERMANN"))
                .body("age", is(6))
                .body("healthStatus", is("Healthy"))
                .body("habitatName", is("Habitat1"));
    }

    @Test
    public void testDeleteTortoise() {
        String accessToken = obtainAccessToken(); // Use the correct username and password

        UUID id = UUID.fromString("33333333-3333-3333-3333-333333333333"); // Assume this ID exists

        given()
                .auth().oauth2(accessToken)
                .pathParam("id", id)
                .when()
                .delete("/tortoises/{id}")
                .then()
                .statusCode(200);

        // Optionally, verify it's actually deleted
        given()
                .auth().oauth2(accessToken)
                .pathParam("id", id)
                .when()
                .get("/tortoises/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testCreateTortoiseWithInvalidData() {
        String accessToken = obtainAccessToken(); // Use the correct username and password

        var invalidTortoise = new TortoiseRequest(null,
                null, null,
                null, null);

        given()
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body(invalidTortoise)
                .when()
                .post("/tortoises")
                .then()
                .statusCode(400);
    }

    @Test
    public void testInvalidAuthReturns401() {
        String invalidAccessToken = "invalid_token";

        given()
                .auth().oauth2(invalidAccessToken)
                .when()
                .get("/tortoises")
                .then()
                .statusCode(401); // Expecting 401 Unauthorized due to invalid token
    }
}
