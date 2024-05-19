package tortoisemonitor.demo.integration_test;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import tortoisemonitor.demo.domain.activity_log.ActivityLogRequest;
import tortoisemonitor.demo.domain.activity_log.ActivityType;

import java.time.LocalDateTime;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@Sql("/test-data/cleanup.sql")
@Sql("/test-data/base-data.sql")
public class ActivityLogIntegrationTest extends BaseIntegrationTest {

    @Test
    public void testGetAllActivityLogs() {
        given()
                .when()
                .get("/activityLogs")
                .then()
                .statusCode(200)
                .body("size()", is(5))
                .body("activityType", containsInAnyOrder("FEEDING", "BASKING", "SLEEPING", "EXPLORING", "SWIMMING"));
    }

    @Test
    public void testCreateActivityLog() {
        var newActivityLog = new ActivityLogRequest(
                null,
                ActivityType.FEEDING,
                LocalDateTime.of(2024, 1, 1, 10, 0, 0),
                LocalDateTime.of(2024, 1, 1, 10, 30, 0),
                "Feeding time");
        String idString = given()
                .contentType(ContentType.JSON)
                .body(newActivityLog)
                .when()
                .post("/activityLogs")
                .then()
                .statusCode(201)
                .extract()
                .path("uuid");

        UUID id = UUID.fromString(idString);

        when()
                .get("/activityLogs/" + id)
                .then()
                .statusCode(200)
                .body("uuid", is(id.toString()))
                .body("activityType", is("FEEDING"))
                .body("startTime", is("2024-01-01T10:00:00"))
                .body("endTime", is("2024-01-01T10:30:00"))
                .body("notes", is("Feeding time"));
    }

    @Test
    public void testCreateActivityLogInvalidRequest() {
        var newActivityLog = new ActivityLogRequest(
                null,
                ActivityType.FEEDING,
                LocalDateTime.of(2024, 1, 1, 10, 0, 0),
                LocalDateTime.of(2024, 1, 1, 10, 30, 0),
                null);
        given()
                .contentType(ContentType.JSON)
                .body(newActivityLog)
                .when()
                .post("/activityLogs")
                .then()
                .statusCode(400);
    }

    @Test
    public void testGetActivityById() {
        UUID id = UUID.fromString("66666666-6666-6666-6666-666666666666"); // Use a valid UUID from setup data

        given()
                .pathParam("id", id)
                .when()
                .get("/activityLogs/{id}")
                .then()
                .statusCode(200)
                .body("uuid", is(id.toString()))
                .body("activityType", is("FEEDING"));
    }

    @Test
    public void testGetActivityByIdInvalid() {
        UUID id = UUID.fromString("66666656-6666-6666-6666-666666666666"); // Use a valid UUID from setup data

        given()
                .pathParam("id", id)
                .when()
                .get("/activityLogs/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testUpdateActivity() {
        UUID tortoiseId = UUID.fromString("55555555-5555-5555-5555-555555555555"); // Assume this ID exists
        UUID activityId = UUID.fromString("66666666-6666-6666-6666-666666666666"); // Assume this ID exists
        ActivityLogRequest updatedLog = new ActivityLogRequest(
                tortoiseId, ActivityType.EXPLORING, LocalDateTime.of(2024, 1, 1, 12, 0, 0),
                LocalDateTime.of(2024, 1, 1, 12, 30, 0), "Exploring time updated");

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", activityId)
                .body(updatedLog)
                .when()
                .put("/activityLogs/{id}")
                .then()
                .statusCode(200)
                .body("activityType", is("EXPLORING"))
                .body("notes", is("Exploring time updated"));
    }

    @Test
    public void testDeleteActivity() {
        UUID id = UUID.fromString("66666666-6666-6666-6666-666666666666"); // Assume this ID exists

        given()
                .pathParam("id", id)
                .when()
                .delete("/activityLogs/{id}")
                .then()
                .statusCode(200);

        // Optionally, verify it's actually deleted
        given()
                .pathParam("id", id)
                .when()
                .get("/activityLogs/{id}")
                .then()
                .statusCode(404);
    }


}
