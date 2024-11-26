package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomApiListener.withCustomTempeletes;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.notNullValue;

public class LoginSpecs {

    public static RequestSpecification loginRequestSpec = with()
            .log().uri()
            .log().headers()
            .log().body()
            .filter(withCustomTempeletes())
            .contentType(ContentType.JSON)
            .baseUri("https://reqres.in")
            .basePath("/api");

    public static ResponseSpecification loginResponse = new ResponseSpecBuilder()
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .expectStatusCode(200)
            .expectBody("token", notNullValue())
            .build();
}
