package apiHelpers;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiHelp {
    private final InfoApi info = new InfoApi();

    public RequestSpecification bodyForAuth() {
        return given()
                .spec(info.getBookingSpec())
                .body("""
                        {
                        "username": "admin",
                        "password": "password123"
                        }
                        """);
    }

    public RequestSpecification bodyForPost() {
        return given()
                .spec(info.getBookingSpec())
                .body("""
                        {
                        "firstname": "Nikita",
                        "lastname": "QA",
                        "totalprice": 100,
                        "depositpaid": true,
                        "bookingdates" : {
                            "checkin" : "2027-01-01",
                            "checkout" : "2028-01-01"
                          },
                        "additionalneeds": "Breakfast"
                        }
                        """);
    }

    public int getIdOfCreatedBooking() {
        Response response2 = bodyForPost()
                .when().post("/booking")
                .then()
                .statusCode(200)
                .body("booking.firstname", equalTo("Nikita"))
                .extract().response();

        JsonPath path2 = response2.jsonPath();

        return path2.getInt("bookingid");
    }

    public String getAuthToken() {
        Response response = bodyForAuth()
                .when().post("/auth")
                .then()
                .statusCode(200)
                .body("token", allOf(
                        notNullValue(),
                        not(emptyString())
                ))
                .extract().response();

        JsonPath path = response.jsonPath();

        return path.getString("token");
    }

    public RequestSpecification bodyForPut() {

        return given()
                .spec(info.getBookingSpec())
                .body("""
                       {
                        "firstname": "asd",
                        "lastname": "123",
                        "totalprice": 100,
                        "depositpaid": true,
                        "bookingdates" : {
                            "checkin" : "2027-01-01",
                            "checkout" : "2028-01-01"
                          },
                        "additionalneeds": "Breakfast"
                        }
                        """);
    }

    public Response getPostByPath(int id) {
        return given()
                .spec(info.getPlaceholderSpec())
                .pathParam("id", id)
                .when()
                .get("/posts/{id}");
    }

    public Response getPostOfUserFromAllPosts(int userId) {
        return given()
                .spec(info.getPlaceholderSpec())
                .queryParam("userId", userId)
                .when()
                .get("/posts");
    }

    public Response getPostByTwoQuery(int userId, int id) {
        return given()
                .spec(info.getPlaceholderSpec())
                .queryParam("userId", userId)
                .queryParam("id", id)
                .when()
                .get("/posts");
    }

    public Response makePost() {
        return given()
                .spec(info.getPlaceholderSpec())
                .body("""
                        {
                        "title": "First create",
                        "body": "Privet",
                        "userId": 1
                        }
                        """)
                .when()
                .post("/posts");
    }
}
