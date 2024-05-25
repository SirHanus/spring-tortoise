package tortoisemonitor.demo.integration_test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseIntegrationTest {

    private static final Logger LOGGER = Logger.getLogger(BaseIntegrationTest.class.getName());
    private static final String BASE_URI = "http://localhost";

    @LocalServerPort
    private int port;

    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @BeforeEach
    public void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
        LOGGER.info("Configured RestAssured with base URI: " + BASE_URI + " and port: " + port);
    }

    protected String obtainAccessToken() {
        LOGGER.info("Obtaining access token for user: " + "owner");

        Map<String, String> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("username", "owner");
        params.put("password", "54321");
        params.put("grant_type", "password");


        LOGGER.info("Request params: " + params);
        LOGGER.info("Keycloak token URL: " + keycloakServerUrl + "/realms/" + realm + "/protocol/openid-connect/token");

        Response response = RestAssured
                .given()
                .contentType("application/x-www-form-urlencoded")
                .formParams(params)
                .when()
                .post(keycloakServerUrl + "/realms/" + realm + "/protocol/openid-connect/token");

        LOGGER.info("Response status: " + response.getStatusCode());

        return response.jsonPath().getString("access_token");
    }
}
