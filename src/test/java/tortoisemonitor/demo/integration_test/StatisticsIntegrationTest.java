package tortoisemonitor.demo.integration_test;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.time.DayOfWeek;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Sql("/test-data/cleanup.sql")
@Sql("/test-data/base-data.sql")
public class StatisticsIntegrationTest extends BaseIntegrationTest {

    @Test
    public void testGetAverageTemperaturePerHabitat() {
        String accessToken = obtainAccessToken(); // Use the correct username and password

        given()
                .auth().oauth2(accessToken)
                .when()
                .get("/statistics/average-temperature")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("11111111-1111-1111-1111-111111111111 - Habitat1", is(22.00f))
                .body("22222222-2222-2222-2222-222222222222 - Habitat2", is(24.00f));
    }

    @Test
    public void testGetTotalActivityDurationPerTortoise() {
        String accessToken = obtainAccessToken(); // Use the correct username and password

        given()
                .auth().oauth2(accessToken)
                .when()
                .get("/statistics/total-activity-duration")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("33333333-3333-3333-3333-333333333333 - Tortoise1", is(150))
                .body("44444444-4444-4444-4444-444444444444 - Tortoise2", is(150))
                .body("55555555-5555-5555-5555-555555555555 - Tortoise3", is(150));
    }

    @Test
    public void testGetMostCommonActivityTypePerTortoise() {
        String accessToken = obtainAccessToken(); // Use the correct username and password

        given()
                .auth().oauth2(accessToken)
                .when()
                .get("/statistics/most-common-activity")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("33333333-3333-3333-3333-333333333333 - Tortoise1", is("FEEDING"))
                .body("44444444-4444-4444-4444-444444444444 - Tortoise2", is("BASKING"))
                .body("55555555-5555-5555-5555-555555555555 - Tortoise3", is("SLEEPING"));
    }

    @Test
    public void testGetAverageAgePerHabitat() {
        String accessToken = obtainAccessToken(); // Use the correct username and password

        given()
                .auth().oauth2(accessToken)
                .when()
                .get("/statistics/average-age")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("11111111-1111-1111-1111-111111111111 - Habitat1", is(3.5F))
                .body("22222222-2222-2222-2222-222222222222 - Habitat2", is(3F));
    }

    @Test
    public void testGetActivityDistributionPerDay() {
        String accessToken = obtainAccessToken(); // Use the correct username and password

        given()
                .auth().oauth2(accessToken)
                .when()
                .get("/statistics/activity-distribution")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(DayOfWeek.MONDAY.toString(), is(3))
                .body(DayOfWeek.TUESDAY.toString(), is(3))
                .body(DayOfWeek.WEDNESDAY.toString(), is(3))
                .body(DayOfWeek.THURSDAY.toString(), is(3))
                .body(DayOfWeek.FRIDAY.toString(), is(3));
    }

    @Test
    public void testInvalidAuthReturns401() {
        String invalidAccessToken = "invalid_token";

        given()
                .auth().oauth2(invalidAccessToken)
                .when()
                .get("/statistics/average-temperature")
                .then()
                .statusCode(401); // Expecting 401 Unauthorized due to invalid token
    }
}
