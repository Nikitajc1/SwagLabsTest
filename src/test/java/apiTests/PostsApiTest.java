package apiTests;

import apiHelpers.ApiHelp;
import apiHelpers.InfoApi;
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
        help.getPost()
                .then()
                .statusCode(200)
                .body("userId", equalTo(1))
                .body("id", equalTo(1))
                .body("body", notNullValue())
                .body("body", not(emptyString()))
                .body("title", not(emptyString()));
    }

    @Test
    void getResponseOfPost() {
        Response response = help.getPost()
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
}
