import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.lombok.LoginBodyLombokModel;
import models.pojo.LoginBodyPojoModel;
import models.pojo.LoginResponsePojoModel;
import models.pojo.LoginResponsePojoModelUnsec;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static helpers.CustomApiListener.withCustomTempeletes;
import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static specs.LoginSpecs.loginRequestSpec;
import static specs.LoginSpecs.loginResponse;

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
        LoginBodyPojoModel data = new LoginBodyPojoModel();
        data.setEmail("eve.holt@reqres.in");
        data.setPassword("cityslicka");

        LoginResponsePojoModel responseModel = given()
                .log().uri()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponsePojoModel.class);

        assertThat(responseModel.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");

    }

    @DisplayName("Логин с боди моделью")
    @Test
    void loginWithModelWithAllureTest() {
        LoginBodyPojoModel data = new LoginBodyPojoModel();
        data.setEmail("eve.holt@reqres.in");
        data.setPassword("cityslicka");

        LoginResponsePojoModel responseModel = given()
                .log().uri()
                .log().headers()
                .log().body()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponsePojoModel.class);

        assertThat(responseModel.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");

    }

    @DisplayName("Логин с боди моделью")
    @Test
    void loginWithCustomModelWithAllureTest() {
        LoginBodyPojoModel data = new LoginBodyPojoModel();
        data.setEmail("eve.holt@reqres.in");
        data.setPassword("cityslicka");

        LoginResponsePojoModel responseModel = given()
                .log().uri()
                .log().headers()
                .log().body()
                .filter(withCustomTempeletes())
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponsePojoModel.class);

        assertThat(responseModel.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");

    }

    @DisplayName("Логин с боди моделью")
    @Test
    void loginWithSpecsTest() {
        LoginBodyPojoModel data = new LoginBodyPojoModel();
        data.setEmail("eve.holt@reqres.in");
        data.setPassword("cityslicka");

        LoginResponsePojoModel responseModel = given(loginRequestSpec)
                .body(data)
                .when()
                .post("/login")
                .then()
                .spec(loginResponse)
                .extract().as(LoginResponsePojoModel.class);

        assertThat(responseModel.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");

    }

    @DisplayName("Логин с боди моделью")
    @Test
    void loginWithSpecsTestUnsecsessful() {
        LoginBodyPojoModel data = new LoginBodyPojoModel();
        data.setEmail("peter@klaven");

        LoginResponsePojoModel responseModelun = given(loginRequestSpec)
                .body(data)
                .when()
                .post("/login")
                .then()
                .spec(loginResponse)
                .extract().as(LoginResponsePojoModel.class);

        assertThat(responseModelun.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");

    }

//    @DisplayName("Логин с боди моделью")
//    @Test
//    void loginWithModelTest() {
//        LoginBodyLombokModelModel data = new LoginBodyLombokModel();
//        data.setEmail("eve.holt@reqres.in");
//        data.setPassword("cityslicka");
//
//        LoginBodyLombokModel responseModel = given()
//                .log().uri()
//                .contentType(ContentType.JSON)
//                .body(data)
//                .when()
//                .post("https://reqres.in/api/login")
//                .then()
//                .log().status()
//                .log().body()
//                .statusCode(200)
//                .extract().as(LoginResponsePojoModel.class);
//
//        assertThat(responseModel.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
//
//    }

//    @BeforeAll
//    public static void setUp(){
//        RestAssured.filters(withCustomTempeletes());
//        baseURI = "";
//        basePath = "/api";
//    }
}
