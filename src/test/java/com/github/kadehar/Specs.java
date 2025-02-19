package com.github.kadehar;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.containsString;

public class Specs {

    public static RequestSpecification requestSpecification = with()
            .baseUri("https://reqres.in/")
            .basePath("/api")
            .log().uri()
            .log().headers()
            .log().all()
            .contentType(ContentType.JSON);

    public static ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .expectStatusCode(200)
            //.expectBody(containsString("success"))
            .build();
}
