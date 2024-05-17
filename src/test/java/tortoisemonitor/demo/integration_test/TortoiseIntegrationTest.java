package tortoisemonitor.demo.integration_test;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import tortoisemonitor.demo.domain.tortoise.TortoiseRequest;
import tortoisemonitor.demo.domain.tortoise.TortoiseSpecies;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@Sql("/test-data/cleanup.sql")
@Sql("/test-data/base-data.sql")
public class TortoiseIntegrationTest extends BaseIntegrationTest {

    @Test
    public void testGetAllTortoises() {
        given()
                .when()
                .get("/tortoises")
                .then()
                .statusCode(200)
                .body("size()", is(3))
                .body("name", containsInAnyOrder("Tortoise1", "Tortoise2", "Tortoise3"));
    }

    @Test
    public void testCreateTortoise() {
        var newTortoise = new TortoiseRequest("Tortoise4",
                TortoiseSpecies.HERMANN, 5,
                "Healthy", null, null);
        UUID id = given()
                .contentType(ContentType.JSON)
                .body(newTortoise)
                .when()
                .post("/tortoises")
                .then()
                .statusCode(201)
                .extract()
                .path("uuid");

        when()
                .get("/tortoises/" + id)
                .then()
                .statusCode(200)
                .body("uuid", is(id.toString()))
                .body("name", is("Tortoise4"))
                .body("species", is("SPECIES1"))
                .body("age", is(5))
                .body("healthStatus", is("Healthy"))
                .body("habitatId", is("habitat-uuid1"));
    }

    // More tests...
}
