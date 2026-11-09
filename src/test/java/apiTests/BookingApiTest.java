package apiTests;

import apiHelpers.ApiHelp;
import apiHelpers.InfoApi;
import apiHelpers.InfoResponse;
import models.Booking;
import models.BookingDates;
import models.BookingResponse;
import models.BookingResponseData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class BookingApiTest {
    private final InfoApi info = new InfoApi();
    private final ApiHelp help = new ApiHelp();
    private String token;
    private Integer bookingID;
    private final InfoResponse infoResponse = new InfoResponse();

    @BeforeEach
    void setUp() {
        token = help.getAuthToken();
        bookingID = help.getIdOfCreatedBooking();
    }

    @AfterEach
    void tearDown() {
        if (bookingID != null) {
            given()
                    .spec(info.getBookingSpec())
                    .cookie("token", token)
                    .when().delete("/booking/" + bookingID)
                    .then()
                    .statusCode(201);
        }
    }

    @Test
    void getBookingById() {

        given()
                .log().all()
                .spec(info.getBookingSpec())
                .when().get("/booking/" + bookingID)
                .then()
                .log().body()
                .spec(infoResponse.getDefaultResponseSpec())
                .body(matchesJsonSchemaInClasspath("schemas/booking-schema.json"))
                .body("firstname", equalTo("Nikita"));
    }

    @Test
    void putBooking() {

        help.bodyForPut()
                .cookie("token", token)
                .when().put("/booking/" + bookingID)
                .then()
                .statusCode(200)
                .body("firstname", allOf(
                        notNullValue(),
                        equalTo("asd")
                ))
                .body("lastname", allOf(
                        notNullValue(),
                        equalTo("123")
                ));

        given()
                .spec(info.getBookingSpec())
                .when().get("/booking/" + bookingID)
                .then()
                .statusCode(200)
                .body("firstname", equalTo("asd"))
                .body("lastname", equalTo("123"));
    }

    @Test
    void deleteBooking() {
        int deletedID = bookingID;

        given()
                .spec(info.getBookingSpec())
                .cookie("token", token)
                .when().delete("/booking/" + deletedID)
                .then()
                .log().all()
                .statusCode(201);

        bookingID = null;

        given()
                .spec(info.getBookingSpec())
                .when().get("/booking/" + deletedID)
                .then()
                .statusCode(404);
    }

    @Test
    void deleteWithoutAuth() {

        given()
                .spec(info.getBookingSpec())
                .when().delete("/booking/" + bookingID)
                .then()
                .statusCode(403);

        given()
                .spec(info.getBookingSpec())
                .when().get("/booking/" + bookingID)
                .then()
                .statusCode(200);
    }

    @Test
    void createBookingWithPojo() {
        BookingDates testDates = new BookingDates("2026-11-10", "2026-11-15");
        Booking testObject = new Booking(
                "Nikita",
                "Test",
                150,
                true,
                testDates,
                "Breakfast"
        );

        given()
                .spec(info.getBookingSpec())
                .body(testObject)
                .when().post("/booking")
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    void createAndDeserializeBooking() {
        Booking testObject = help.createBooking();

        BookingResponse response = given()
                .spec(info.getBookingSpec())
                .body(testObject)
                .when().post("/booking")
                .then()
                .statusCode(200)
                .extract()
                .as(BookingResponse.class);

        BookingResponseData booking = response.getBooking();

        assertAll(
                () -> assertEquals(150, booking.getTotalprice()),
                () -> assertTrue(booking.isDepositpaid()),
                () -> assertEquals("Nikita", booking.getFirstname()),
                () -> assertEquals("Test", booking.getLastname()),
                () -> assertTrue(response.getBookingid() > 0),
                () -> assertEquals("2026-11-10", booking
                        .getBookingdates().getCheckin()),
                () -> assertEquals("2026-11-15", booking
                        .getBookingdates().getCheckout()),
                () -> assertEquals("Breakfast", booking.getAdditionalneeds()),
                () -> assertNotNull(response.getBooking()),
                () -> assertNotNull(response.getBooking().getBookingdates())
        );
    }

    @Test
    void putBookingWithPojo() {
        Booking testObject = help.createBooking();

        BookingResponse response = given()
                .spec(info.getBookingSpec())
                .body(testObject)
                .when().post("/booking")
                .then()
                .statusCode(200)
                .extract()
                .as(BookingResponse.class);

        bookingID = response.getBookingid();

        Booking updatedObject = help.uptadedBooking();

        BookingResponseData booking = given()
                .spec(info.getBookingSpec())
                .cookie("token", token)
                .body(updatedObject)
                .when().put("/booking/" + bookingID)
                .then()
                .statusCode(200)
                .extract()
                .as(BookingResponseData.class);

        assertAll(
                () -> assertEquals(250, booking.getTotalprice()),
                () -> assertFalse(booking.isDepositpaid()),
                () -> assertEquals("Updated", booking.getFirstname()),
                () -> assertEquals("User", booking.getLastname()),
                () -> assertTrue(response.getBookingid() > 0),
                () -> assertEquals("2026-12-01", booking
                        .getBookingdates().getCheckin()),
                () -> assertEquals("2026-12-05", booking
                        .getBookingdates().getCheckout()),
                () -> assertEquals("Dinner", booking.getAdditionalneeds()),
                () -> assertNotNull(booking),
                () -> assertNotNull(booking.getBookingdates())
        );
    }

}
