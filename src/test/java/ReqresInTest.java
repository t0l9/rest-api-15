import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

public class ReqresInTest {

    @Test
    void chechAuthPositive(){

        String data = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"cityslicka\"\n" +
                "}";

        String token = "QpwL5tke4Pnpja7X4";

        given()
                .log().uri()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is(token));

    }

    @Test
    void missingPasswordTest(){

        String data = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "}";

        String token = "QpwL5tke4Pnpja7X4";

        given()
                .log().uri()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("token", is(token));

    }

    //Разработать автотесты 5 штук

    @Test
    void checkSingleUserNotFound(){
        String userNumber = "23";

        given()
                .log().uri()
                .contentType(ContentType.JSON)
                .when()
                .get("https://reqres.in/api/users/" + userNumber)
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }

    @Test
    void createUser(){
        String name = "Anatoliy";

        String data = "{\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";

        given()
                .log().uri()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is(name));
    }

}
