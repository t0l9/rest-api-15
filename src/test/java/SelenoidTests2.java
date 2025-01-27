import io.qameta.allure.Allure;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import models.pojo.LoginBodyPojoModel;
import models.pojo.LoginResponsePojoModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static helpers.CustomApiListener.withCustomTempeletes;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static specs.LoginSpecs.loginRequestSpec;
import static specs.LoginSpecs.loginResponse;

public class SelenoidTests2 {

    /* 1 Make request = https://selenoid.autotests.cloud/status
       2 get response 
       3 Check total is 20
     */

    @Test
    void chechTotal() {

        get("https://selenoid.autotests.cloud/status")
                .then()
                .body("total", is(20));

    }

    @Test
    void chechTotalWithStatus() {

        get("https://selenoid.autotests.cloud/status")
                .then()
                .statusCode(200)
                .body("total", is(20));

    }

    @Test
    void chechTotalWithGiven() {

        given()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .statusCode(200)
                .body("total", is(20));

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
                .body("total", is(20));

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
                .body("total", is(20));

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


    @Test
    void checkModelHubStatus401(){
        given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(401);
    }

    @Test
    void checkModelHubStatus(){
        given()
                .log().uri()
                .when()
                .get("https://user1:1234@selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("value.ready", is(true));
    }

    @Test
    void checkModelHubStatusWithAuth(){
        given()
                .log().uri()
                .auth().basic("user1", "1234")
                .when()
                .get("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("value.ready", is(true));
    }




}
