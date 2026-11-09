package apiTests;

import apiHelpers.ConfigReader;
import apiHelpers.InfoApi;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthTests {
    private final InfoApi info = new InfoApi();

    @Test
    void basicAuthTestSuccess() {
        given()
                .spec(info.getHttpAuthSpec())
                .auth().basic("admin", "12345")
                .when().get("/basic-auth/admin/12345")
                .then()
                .statusCode(200)
                .body("authenticated", is(true))
                .body("user", equalTo("admin"));
    }

    @Test
    void basicAuthFail() {
        given()
                .spec(info.getHttpAuthSpec())
                .auth().basic("123", "123")
                .when().get("/basic-auth/admin/12345")
                .then()
                .statusCode(401);
    }

    @Test
    void bearerAuthSuccess() {
        String token = "my-secret-token";

        given()
                .spec(info.getHttpAuthSpec())
                .auth().oauth2(token)
                .when().get("/bearer")
                .then()
                .statusCode(200)
                .body("authenticated", is(true))
                .body("token", equalTo(token));
    }

    @Test
    void bearerAuthFail() {
        given()
                .spec(info.getHttpAuthSpec())
                .when().get("/bearer")
                .then()
                .statusCode(401);
    }

    @Test
    void configReaderTest() throws IOException {
        ConfigReader config = new ConfigReader();

        System.out.println(config.getProperty("booking.baseUrl"));
        System.out.println(config.getProperty("httpbin.baseUrl"));
    }

}
