package apiTests;

import apiHelpers.ApiHelp;
import apiHelpers.InfoApi;
import org.junit.jupiter.api.Test;

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
                .when().get("/booking/99999999999999")
                .then()
                .statusCode(404);
    }

    @Test
    void incorrectAuth() {
        given()
                .spec(info.getBookingSpec())
                .body("""
                        {
                        "username": "admin",
                        "password": "123asd1123123"
                        }
                        """)
                .when().post("/auth")
                .then()
                .statusCode(200)
                .body("reason", equalTo("Bad credentials"));
    }
}
