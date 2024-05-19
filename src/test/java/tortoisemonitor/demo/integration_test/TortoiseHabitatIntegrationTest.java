package tortoisemonitor.demo.integration_test;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import tortoisemonitor.demo.domain.TortoiseHabitat.TortoiseHabitatRequest;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@Sql("/test-data/cleanup.sql")
@Sql("/test-data/base-data.sql")
public class TortoiseHabitatIntegrationTest extends BaseIntegrationTest {

    @Test
    public void testGetAllHabitats() {
        given()
                .when()
                .get("/tortoiseHabitats")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("name", containsInAnyOrder("Habitat1", "Habitat2"));
    }

    @Test
    public void testCreateHabitat() {
        var newHabitat = new TortoiseHabitatRequest("Habitat3");
        String stringId = given()
                .contentType(ContentType.JSON)
                .body(newHabitat)
                .when()
                .post("/tortoiseHabitats")
                .then()
                .statusCode(201)
                .extract()
                .path("uuid");

        UUID id = UUID.fromString(stringId);

        when()
                .get("/tortoiseHabitats/" + id)
                .then()
                .statusCode(200)
                .body("uuid", is(id.toString()))
                .body("name", is("Habitat3"));
    }

    @Test
    public void testCreateHabitatInvalidRequestNull() {
        var newHabitat = new TortoiseHabitatRequest(null);
        given()
                .contentType(ContentType.JSON)
                .body(newHabitat)
                .when()
                .post("/tortoiseHabitats")
                .then()
                .statusCode(400);

    }
    @Test
    public void testCreateHabitatInvalidRequestEmpty() {
        var newHabitat = new TortoiseHabitatRequest("");
        given()
                .contentType(ContentType.JSON)
                .body(newHabitat)
                .when()
                .post("/tortoiseHabitats")
                .then()
                .statusCode(400);

    }

    @Test
    public void testGetHabitatById() {
        UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111"); // Use a valid UUID from setup data

        given()
                .pathParam("id", id)
                .when()
                .get("/tortoiseHabitats/{id}")
                .then()
                .statusCode(200)
                .body("uuid", is(id.toString()))
                .body("name", is("Habitat1"));
    }

    @Test
    public void testGetHabitatByInvalidId() {
        UUID invalidId = UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff"); // Non-existent ID

        given()
                .pathParam("id", invalidId)
                .when()
                .get("/tortoiseHabitats/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testUpdateHabitat() {
        UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111"); // Assume this ID exists
        var updatedHabitat = new TortoiseHabitatRequest("UpdatedHabitat1");

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(updatedHabitat)
                .when()
                .put("/tortoiseHabitats/{id}")
                .then()
                .statusCode(200)
                .body("name", is("UpdatedHabitat1"));
    }

    @Test
    public void testDeleteHabitat() {
        UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111"); // Assume this ID exists

        given()
                .pathParam("id", id)
                .when()
                .delete("/tortoiseHabitats/{id}")
                .then()
                .statusCode(200);

        given()
                .pathParam("id", id)
                .when()
                .get("/tortoiseHabitats/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testCreateHabitatWithInvalidData() {
        // Attempt to create a habitat with missing required fields
        var invalidHabitat = new TortoiseHabitatRequest(null);

        given()
                .contentType(ContentType.JSON)
                .body(invalidHabitat)
                .when()
                .post("/tortoiseHabitats")
                .then()
                .statusCode(400); // Expecting a 400 Bad Request due to invalid input
    }
}
