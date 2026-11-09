package apiHelpers;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

public class InfoResponse {

    private final ResponseSpecification defaultResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON)
            .build();

    private final ResponseSpecification notFoundResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .expectContentType("text/plain")
            .build();

    public ResponseSpecification getDefaultResponseSpec() {
        return defaultResponseSpec;
    }

    public ResponseSpecification getNotFoundResponseSpec() {
        return notFoundResponseSpec;
    }
}
