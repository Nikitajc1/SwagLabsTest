package apiTests;

import apiHelpers.ApiHelp;
import apiHelpers.InfoApi;
import apiHelpers.InfoResponse;
import models.Booking;
import models.BookingDates;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthBookingApi {
    private final InfoApi info = new InfoApi();
    private final ApiHelp help = new ApiHelp();
    private final InfoResponse infoResponse = new InfoResponse();


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
                .when().get("/booking/123123123")
                .then()
                .log().ifError()
                .spec(infoResponse.getNotFoundResponseSpec());
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

    static Stream<Booking> bookingData() {
        return Stream.of(
                new Booking("Nikita", "Test", 150, true,
                        new BookingDates("2026-11-10",
                                "2026-11-15"), "Breakfast"),

                new Booking("Alex", "Smith", 250, false,
                        new BookingDates("2026-12-01",
                                "2026-12-05"), "Dinner")
        );
    }

    @ParameterizedTest
    @MethodSource("bookingData")
    void createBookingWithMethodParam(Booking booking) {
        given()
                .spec(info.getBookingSpec())
                .body(booking)
                .when().post("/booking")
                .then()
                .statusCode(200)
                .body("booking.firstname", not(emptyString()))
                .body("booking.lastname", not(emptyString()))
                .body("booking.totalprice", greaterThan(0));
    }
}
