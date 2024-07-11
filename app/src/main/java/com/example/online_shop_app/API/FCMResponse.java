package com.example.online_shop_app.API;

public class FCMResponse {
    private  String name;

    public FCMResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
