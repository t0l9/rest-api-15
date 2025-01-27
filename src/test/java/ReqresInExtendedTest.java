import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import models.LoginBodyModel;
import models.LoginResponseModel;
import models.lombok.LoginBodyLombokModel;
import models.lombok.LoginResponseLombokModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;

public class ReqresInExtendedTest {

    @Test
    void loginWithBadPracticeTest(){

        String data = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"cityslicka\"\n" +
                "}"; //todo move to model

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
    void loginWithModelTest(){

        //В моделс класс модель
        LoginBodyModel data = new LoginBodyModel();
        data.setEmail("eve.holt@reqres.in");
        data.setPassword("cityslicka");


        LoginResponseModel response = given()
                .log().uri()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseModel.class);

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");

    }

//    @Test
//    void loginWithLombokModelTest(){
//
//        //В моделс класс модель Ломбок не нужно ставить геттер и сеттер
//        LoginBodyLombokModel data = new LoginBodyLombokModel();
//        data.setEmail("eve.holt@reqres.in");
//        data.setPassword("cityslicka");
//
//
//        LoginResponseLombokModel response = given()
//                .log().uri()
//                .contentType(ContentType.JSON)
//                .body(data)
//                .when()
//                .post("https://reqres.in/api/login")
//                .then()
//                .log().status()
//                .log().body()
//                .statusCode(200)
//                .extract().as(LoginResponseLombokModel.class);
//
//        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
//
//    }

    @Test
    void loginWithModelTestAllure(){

        //В моделс класс модель
        LoginBodyModel data = new LoginBodyModel();
        data.setEmail("eve.holt@reqres.in");
        data.setPassword("cityslicka");


        //Добавили Аллюр
        LoginResponseModel response = given()
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
                .extract().as(LoginResponseModel.class);

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");

    }
}
