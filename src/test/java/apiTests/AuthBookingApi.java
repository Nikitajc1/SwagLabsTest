package apiTests;

import apiHelpers.ApiHelp;
import apiHelpers.InfoApi;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthBookingApi {
    private final InfoApi info = new InfoApi();
    private final ApiHelp help = new ApiHelp();

    @Test
    void testToken() {
        help.bodyForAuth()
                .then()
                .statusCode(200)
                .body("token", allOf(
                        notNullValue(),
                        not(emptyString())
                ));
    }

    @Test
    void getFakeBooking() {
        given()
                .spec(info.getBookingSpec())
                .pathParam("id", "99999999999999")
                .when().get("/booking/{id}")
                .then()
                .statusCode(404)
                .body(containsString("Not Found"));
    }

    @Test
    void incorrectAuth() {
        help.bodyForFakeAuth()
                .when().post("/auth")
                .then()
                .statusCode(200)
                .body("reason", equalTo("Bad credentials"));
    }

    @Test
    void incorrectPost() {
        help.bodyForFakePost()
                .when().post("/booking")
                .then()
                .statusCode(500)
                .contentType("text/plain")
                .body(containsString("Internal Server Error"));
    }

    @ParameterizedTest
    @CsvSource({
            "asd, 123",
            "'', ''",
            "asd, password123",
            "admin, asd123"
    })
    void authWithInvalidCredentials(String login, String pass) {
        given()
                .spec(info.getBookingSpec())
                .body("""
                        {
                        "username": "%s",
                        "password": "%s"
                        }
                        """.formatted(login, pass))
                .when().post("/auth")
                .then()
                .statusCode(200)
                .body("reason", equalTo("Bad credentials"));
    }
}
