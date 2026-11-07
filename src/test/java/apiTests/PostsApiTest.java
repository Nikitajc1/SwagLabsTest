package apiTests;

import apiHelpers.ApiHelp;
import apiHelpers.InfoApi;
import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostsApiTest {
    private final InfoApi info = new InfoApi();
    private final ApiHelp help = new ApiHelp();

    @Test
    void getPostById200() {
        help.getPostByPath(1)
                .then()
                .statusCode(200)
                .body("userId", equalTo(1))
                .body("id", equalTo(1))
                .body("body", notNullValue())
                .body("body", not(emptyString()))
                .body("title", not(emptyString()))
                .body("title", equalTo(info.getTitleOfPost1()));
    }

    @Test
    void getResponseOfPost() {
        Response response = help.getPostByPath(1)
                .then()
                .extract().response();

        JsonPath path = response.jsonPath();

        String title = path.getString("title");
        int userId = path.getInt("userId");

        assertEquals(1, userId);
        assertEquals(info.getTitleOfPost1(), title);
    }

    @Test
    void createPost() {
        Response response = help.makePost()
                .then()
                .statusCode(201)
                .header("Content-Type", containsString("application/json"))
                .body("title", equalTo(info.getTestTitle()))
                .extract().response();

        JsonPath path = response.jsonPath();

        int id = path.getInt("id");

        assertAll(
                () -> assertEquals(101, id)
        );
    }

    @Test
    void getPostByPathParam() {
        help.getPostByPath(5)
                .then()
                .statusCode(200)
                .body("userId", equalTo(1))
                .body("id", equalTo(5))
                .body("body", notNullValue())
                .body("body", not(emptyString()))
                .body("title", not(emptyString()))
                .body("title", equalTo(info.getTitleOfPost5()));
    }

    @Test
    void getPostsOfUser() {
        help.getPostOfUserFromAllPosts(2)
                .then()
                .statusCode(200)
                .body("userId", everyItem(equalTo(2)));
    }

    @Test
    void getFiltratedPost() {
        help.getPostByTwoQuery(1, 5)
                .then()
                .statusCode(200)
                .log().all()
                .body("[0].userId", equalTo(1))
                .body("[0].id", equalTo(5))
                .body("[0].title", equalTo(info.getTitleOfPost5()))
                .body("[0].body", equalTo(info.getBodyOfPost5()));
    }
}
