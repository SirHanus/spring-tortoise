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
                .body("content.numberOfAccountsWithAverageOutgoingTransactionGreaterThan100", is(1))
                .body("content.countOfTransactionsLessThan100", is(1))
                .body("content.idsOfUsersNamedIvo", contains(1))
                .body("content.countOfPeopleStartingWithI", is(1))
                .body("content.namesOfUsersWithBalanceGreaterThan150OnOwnedAccounts", contains("Marie"))
                .body("content.idsOfTransactionsWhereIvoIsOwner", containsInAnyOrder("c4fd85db-55c5-4620-b7eb-73191a43520e", "736a5759-f681-40d7-92b5-0dff9327575e", "5fdba127-ab33-4881-bcf8-096e210fe7c9"))
                .body("content.countOfTransactionsWithSameSourceAndTargetAccount", is(0))
                .body("content.usersWithOneAccount", containsInAnyOrder(1, 2))
                .body("content.averageNumberOfOutgoingTransactionsPerUser", is(1.5f));
    }

}