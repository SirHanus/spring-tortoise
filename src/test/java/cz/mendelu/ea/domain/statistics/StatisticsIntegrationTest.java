package cz.mendelu.ea.domain.statistics;

import cz.mendelu.ea.domain.account.AccountRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/test-data/cleanup.sql")
@Sql("/test-data/StatisticsIntegrationTestData.sql")
public class StatisticsIntegrationTest {

    private final static String BASE_URI = "http://localhost";

    @LocalServerPort
    private int port;

    @BeforeEach
    public void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }

    @Test
    public void testGetStatistics() {
        given()
        .when()
                .get("/statistics")
        .then()
                .statusCode(200)
                .body("content.numberOfAccountsWithBalanceGreaterThan150", is(1))
                .body("content.namesOf3UsersWithHighestBalance", contains("Marie", "Ivo"))
                .body("content.numberOfAccountsWithAverageOutgoingTransactionGreaterThan100", is(1));
    }

}