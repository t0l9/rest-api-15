import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.devtools.v127.fetch.model.AuthChallengeResponse;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

public class SelenoidTests {

    /* 1 Make request = https://selenoid.autotests.cloud/status
       2 get response 
       3 Check total is 20
     */

    @Test
    void chechTotal() {
        get("https://selenoid.autotests.cloud/status")
                .then()
                .body("total", is(20))
                .body("used", is(0));

    }


    @Test
    void chechTotalWithStatus() {
        get("https://selenoid.autotests.cloud/status")
                .then()
                .statusCode(200)
                .body("total", is(20))
                .body("used", is(0));

    }

    @Test
    void chechTotalWithGiven() {

        given()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .statusCode(200)
                .body("total", is(20))
                .body("used", is(0));

    }

    @Test
    void chechTotalWithLogs() {

        given()
                .log().all()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("total", is(20))
                .body("used", is(0));

    }

    @Test
    void chechTotalWithSomeLogs() {

        given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("total", is(20))
                .body("used", is(0));

    }

    @Test
    void chechChromeVersion() {

        given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("browsers.chrome", hasKey("100.0"));

    }


    //String response = "{\"total\":20,\"used\":0,\"queued\":0,\"pending\":0,\"browsers\":{\"chrome\":{\"100.0\":{},\"113.0\":{},\"114.0\":{},\"120.0\":{},\"121.0\":{},\"122.0\":{},\"123.0\":{},\"124.0\":{},\"125.0\":{},\"126.0\":{},\"99.0\":{}},\"firefox\":{\"122.0\":{},\"123.0\":{},\"124.0\":{},\"125.0\":{}},\"opera\":{\"106.0\":{},\"107.0\":{},\"108.0\":{},\"109.0\":{}},\"playwright-chromium\":{\"1.28.1\":{}}}}";


    //https://selenoid.autotests.cloud/wd/hub/status



    @DisplayName("Негативный кейс")
    @Test
    void chechHubStatus401() {
        given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(401);
                //.body("value.message", is("Selenoid 1.11.3 built at 2024-05-25_12:34:40PM"));

    }

    @DisplayName("Позитивный кейс")
    @Test
    void chechHubStatus200() {
        given()
                .log().uri()
                .when()
                .get("https://user1:1234@selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("value.message", is("Selenoid 1.11.3 built at 2024-05-25_12:34:40PM"))
                .body("value.ready", is(true));

    }

    @DisplayName("Позитивный кейс")
    @Test
    void chechHubStatusWithAuth() {
        given()
                .log().uri()
                .auth().basic("user1", "1234")
                .when()
                .get("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("value.message", is("Selenoid 1.11.3 built at 2024-05-25_12:34:40PM"))
                .body("value.ready", is(true));

    }


    @DisplayName("Логин с боди моделью")
    @Test
    void loginWithModelTest() {
        LoginBodyModel data = new LoginBodyModel();
        data.setEmail("eve.holt@reqres.in");
        data.setPassword("cityslicka");

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
                .body("token", is("QpwL5tke4Pnpja7X4"));

    }
}
