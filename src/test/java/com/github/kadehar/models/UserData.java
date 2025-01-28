package com.github.kadehar.models;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserData {
    private User data;


    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
