package apiHelpers;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.http.ContentType;
import java.io.IOException;

public class InfoApi {

    private final String titleOfPost1 = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit";
    private final String testTitle = "First create";
    private final String titleOfPost5 ="nesciunt quas odio";
    private final String bodyOfPost5 = "repudiandae veniam quaerat sunt sed\nalias aut fugiat sit autem sed est\nvoluptatem omnis possimus esse voluptatibus quis\nest aut tenetur dolor neque";
    private final ConfigReader config;
    private final RequestSpecification httpAuthSpec;
    private final RequestSpecification bookingSpec;
    private final RequestSpecification placeholderSpec;


    public InfoApi() {
        config = new ConfigReader();

        httpAuthSpec =
                new RequestSpecBuilder()
                        .setBaseUri(getHttpURI())
                        .setAccept("*/*")
                        .setContentType(ContentType.JSON)
                        .build();

        bookingSpec =
                new RequestSpecBuilder()
                        .setBaseUri(getBookingURI())
                        .setAccept("*/*")
                        .setContentType(ContentType.JSON)
                        .build();

        placeholderSpec =
                new RequestSpecBuilder()
                        .setBaseUri(getPlaceholderURI())
                        .setAccept(ContentType.JSON)
                        .setContentType(ContentType.JSON)
                        .build();
    }

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
        return config.getProperty("booking.baseUrl");
    }

    public String getPlaceholderURI() {
        return config.getProperty("placeholder.baseUrl");
    }

    public String getTitleOfPost5() {
        return titleOfPost5;
    }

    public String getBodyOfPost5() {
        return bodyOfPost5;
    }

    public String getHttpURI() {
        return config.getProperty("httpbin.baseUrl");
    }

    public RequestSpecification getHttpAuthSpec() {
        return httpAuthSpec;
    }
}
