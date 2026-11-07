package apiHelpers;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.http.ContentType;

public class InfoApi {

    private final String titleOfPost1 = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit";
    private final String testTitle = "First create";
    private final String bookingURI = "https://restful-booker.herokuapp.com";
    private final String placeholderURI = "https://jsonplaceholder.typicode.com/";

    private final RequestSpecification bookingSpec =
            new RequestSpecBuilder()
                    .setBaseUri(getBookingURI())
                    .setAccept("*/*")
                    .setContentType(ContentType.JSON)
                    .build();

    private final RequestSpecification placeholderSpec =
            new RequestSpecBuilder()
                    .setBaseUri(getPlaceholderURI())
                    .setAccept(ContentType.JSON)
                    .setContentType(ContentType.JSON)
                    .build();

    public RequestSpecification getBookingSpec() {
        return  bookingSpec;
    }

    public RequestSpecification getPlaceholderSpec() {
        return placeholderSpec;
    }


    public String getTitleOfPost1() {
        return titleOfPost1;
    }

    public String getTestTitle() {
        return testTitle;
    }

    public String getBookingURI() {
        return bookingURI;
    }

    public String getPlaceholderURI() {
        return placeholderURI;
    }
}
