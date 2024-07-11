package com.example.online_shop_app.API.Order.Request;

import com.google.gson.annotations.SerializedName;

public class OrderRequest {
    @SerializedName("userId")
    private String userId;

    public OrderRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
