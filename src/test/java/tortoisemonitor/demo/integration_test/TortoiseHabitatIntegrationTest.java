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
        var newHabitat = new TortoiseHabitatRequest("Habitat3", null, null);
        UUID id = given()
                .contentType(ContentType.JSON)
                .body(newHabitat)
                .when()
                .post("/tortoiseHabitats")
                .then()
                .statusCode(201)
                .extract()
                .path("uuid");

        when()
                .get("/tortoiseHabitats/" + id)
                .then()
                .statusCode(200)
                .body("uuid", is(id.toString()))
                .body("name", is("Habitat3"));
    }

    // More tests...
}

