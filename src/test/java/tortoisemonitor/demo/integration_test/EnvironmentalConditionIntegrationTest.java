package tortoisemonitor.demo.integration_test;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import tortoisemonitor.demo.domain.environmental_condition.EnvironmentalConditionRequest;

import java.time.LocalDateTime;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@Sql("/test-data/cleanup.sql")
@Sql("/test-data/base-data.sql")
public class EnvironmentalConditionIntegrationTest extends BaseIntegrationTest {

    @Test
    public void testGetAllEnvironmentalConditions() {
        given()
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
        var newEnvironmentalCondition = new EnvironmentalConditionRequest(
                22.0, 70.0, 12.0,
                LocalDateTime.of(2024, 1, 1, 10, 0, 0), null);
        String stringId = given()
                .contentType(ContentType.JSON)
                .body(newEnvironmentalCondition)
                .when()
                .post("/environment")
                .then()
                .statusCode(201)
                .extract()
                .path("uuid");

        UUID id = UUID.fromString(stringId);

        when()
                .get("/environment/" + id)
                .then()
                .statusCode(200)
                .body("uuid", is(id.toString()))
                .body("temperature", is(22.0f))
                .body("humidity", is(70.0f))
                .body("lightLevel", is(12.0f))
                .body("timestamp", is("2024-01-01T10:00:00"));
    }

    // More tests...
}
