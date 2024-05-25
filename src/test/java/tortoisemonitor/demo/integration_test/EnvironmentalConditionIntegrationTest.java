package tortoisemonitor.demo.integration_test;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import tortoisemonitor.demo.domain.environmental_condition.EnvironmentalConditionRequest;

import java.time.LocalDateTime;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Sql("/test-data/cleanup.sql")
@Sql("/test-data/base-data.sql")
public class EnvironmentalConditionIntegrationTest extends BaseIntegrationTest {

    @Test
    public void testGetAllEnvironmentalConditions() {
        String accessToken = obtainAccessToken(); // Use the correct username and password

        given()
                .auth().oauth2(accessToken)
                .when()
                .get("/environment")
                .then()
                .statusCode(200)
                .body("size()", is(4))
                .body("temperature", everyItem(greaterThanOrEqualTo(20.0f)))
                .body("temperature", everyItem(lessThanOrEqualTo(26.0f)));
    }

    @Test
    public void testCreateEnvironmentalCondition() {
        String accessToken = obtainAccessToken(); // Use the correct username and password

        var newEnvironmentalCondition = new EnvironmentalConditionRequest(
                22.0, 70.0, 12.0,
                LocalDateTime.of(2024, 1, 1, 10, 0, 0), null);
        String stringId = given()
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body(newEnvironmentalCondition)
                .when()
                .post("/environment")
                .then()
                .statusCode(201)
                .extract()
                .path("uuid");

        UUID id = UUID.fromString(stringId);

        given()
                .auth().oauth2(accessToken)
                .when()
                .get("/environment/" + id)
                .then()
                .statusCode(200)
                .body("uuid", is(id.toString()))
                .body("temperature", is(22.0f))
                .body("humidity", is(70.0f))
                .body("lightLevel", is(12.0f))
                .body("timestamp", is("2024-01-01T10:00:00"));
    }

    @Test
    public void testCreateEnvironmentalConditionInvalidTimestamp() {
        String accessToken = obtainAccessToken(); // Use the correct username and password

        var newEnvironmentalCondition = new EnvironmentalConditionRequest(
                22.0, 70.0, 12.0,
                null, null);
        given()
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body(newEnvironmentalCondition)
                .when()
                .post("/environment")
                .then()
                .statusCode(400);
    }

    @Test
    public void testCreateEnvironmentalConditionInvalidHumidity() {
        String accessToken = obtainAccessToken(); // Use the correct username and password

        var newEnvironmentalCondition = new EnvironmentalConditionRequest(
                22.0, -5.0, 12.0,
                null, null);
        given()
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body(newEnvironmentalCondition)
                .when()
                .post("/environment")
                .then()
                .statusCode(400);
    }

    @Test
    public void testCreateEnvironmentalConditionInvalidLightLevel() {
        String accessToken = obtainAccessToken(); // Use the correct username and password

        var newEnvironmentalCondition = new EnvironmentalConditionRequest(
                22.0, 5.0, -12.0,
                null, null);
        given()
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body(newEnvironmentalCondition)
                .when()
                .post("/environment")
                .then()
                .statusCode(400);
    }

    @Test
    public void testGetEnvironmentalConditionById() {
        String accessToken = obtainAccessToken(); // Use the correct username and password
        UUID id = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"); // Use a valid UUID from setup data

        given()
                .auth().oauth2(accessToken)
                .pathParam("id", id)
                .when()
                .get("/environment/{id}")
                .then()
                .statusCode(200)
                .body("uuid", is(id.toString()))
                .body("temperature", is(20.0f))
                .body("humidity", is(65.0f))
                .body("lightLevel", is(10.0f))
                .body("timestamp", is("2024-01-01T10:00:00"));
    }

    @Test
    public void testUpdateEnvironmentalCondition() {
        String accessToken = obtainAccessToken(); // Use the correct username and password
        UUID id = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"); // Assume this ID exists
        EnvironmentalConditionRequest updatedCondition = new EnvironmentalConditionRequest(
                25.0, 75.0, 15.0,
                LocalDateTime.of(2024, 1, 1, 12, 0, 0), UUID.fromString("11111111-1111-1111-1111-111111111111"));

        given()
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(updatedCondition)
                .when()
                .put("/environment/{id}")
                .then()
                .statusCode(200)
                .body("temperature", is(25.0f))
                .body("humidity", is(75.0f))
                .body("lightLevel", is(15.0f))
                .body("timestamp", is("2024-01-01T12:00:00"));
    }

    @Test
    public void testDeleteEnvironmentalCondition() {
        String accessToken = obtainAccessToken(); // Use the correct username and password
        UUID id = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"); // Assume this ID exists

        given()
                .auth().oauth2(accessToken)
                .pathParam("id", id)
                .when()
                .delete("/environment/{id}")
                .then()
                .statusCode(200);

        // Optionally, verify it's actually deleted
        given()
                .auth().oauth2(accessToken)
                .pathParam("id", id)
                .when()
                .get("/environment/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testGetEnvironmentalConditionByInvalidId() {
        String accessToken = obtainAccessToken(); // Use the correct username and password
        UUID invalidId = UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff"); // Non-existent ID

        given()
                .auth().oauth2(accessToken)
                .pathParam("id", invalidId)
                .when()
                .get("/environment/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testCreateEnvironmentalConditionWithInvalidData() {
        String accessToken = obtainAccessToken(); // Use the correct username and password

        // Attempt to create an environmental condition with missing required fields
        var invalidCondition = new EnvironmentalConditionRequest(
                null, null, null,
                LocalDateTime.of(2024, 1, 1, 10, 0, 0), null);

        given()
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body(invalidCondition)
                .when()
                .post("/environment")
                .then()
                .statusCode(400); // Expecting a 400 Bad Request due to invalid input
    }

    @Test
    public void testInvalidAuthReturns401() {
        String invalidAccessToken = "invalid_token";

        given()
                .auth().oauth2(invalidAccessToken)
                .when()
                .get("/environment")
                .then()
                .statusCode(401); // Expecting 401 Unauthorized due to invalid token
    }
}
