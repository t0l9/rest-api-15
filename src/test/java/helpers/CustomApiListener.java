package helpers;

import io.qameta.allure.restassured.AllureRestAssured;

public class CustomApiListener {

    private static final AllureRestAssured FILTER = new AllureRestAssured();

    public static AllureRestAssured withCustomTempeletes(){
        FILTER.setRequestTemplate("request.ftl");
        FILTER.setResponseTemplate("request.ftl");
        return FILTER;
    }
}
