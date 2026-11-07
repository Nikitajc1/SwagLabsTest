package apiTests;
import apiHelpers.ApiHelp;
import apiHelpers.InfoApi;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.allOf;

public class BookingApiTest {
    private final InfoApi info = new InfoApi();
    private final ApiHelp help = new ApiHelp();
    private String token;
    private Integer bookingID;

    @BeforeEach
    void setUp() {
        token = help.getAuthToken();
        bookingID = help.getIdOfCreatedBooking();
    }

    @AfterEach
    void tearDown() {
        if (bookingID != null){
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
                .spec(info.getBookingSpec())
                .when().get("/booking/" + bookingID)
                .then()
                .statusCode(200)
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

}
