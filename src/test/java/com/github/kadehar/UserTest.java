package com.github.kadehar;

import com.github.kadehar.lombok.User;
import com.github.kadehar.models.UserData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Test
    void singleUser(){
        Specs.requestSpecification
                .when()
                .get("/users/2")
                .then()
                .spec(Specs.responseSpecification)
                .log().body();
    }

    @Test
    void listOfUsers(){
        Specs.requestSpecification
                .when()
                .get("/users?page=2")
                .then()
                .log().body();
    }

    @Test
    void singleUserWithModel(){
        UserData data = Specs.requestSpecification
                .when()
                .get("/users/2")
                .then()
                .spec(Specs.responseSpecification)
                .log().body()
                .extract().as(UserData.class);

        assertEquals(3, data.getData().getId());
    }

    @Test
    void singleUserWithLombokModel(){
        com.github.kadehar.lombok.UserData data = Specs.requestSpecification
                .when()
                .get("/users/2")
                .then()
                .spec(Specs.responseSpecification)
                .log().body()
                .extract().as(com.github.kadehar.lombok.UserData.class);

        assertEquals(2, data.getUser().getId());
    }
}
