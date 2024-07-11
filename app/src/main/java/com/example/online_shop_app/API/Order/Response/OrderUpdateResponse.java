package com.example.online_shop_app.API.Order.Response;

import com.example.online_shop_app.Models.Order;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderUpdateResponse {
    private boolean success;
    private int status;
    @SerializedName("data")
    private Order data;

    public OrderUpdateResponse(boolean success, int status, Order data) {
        this.success = success;
        this.status = status;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Order getData() {
        return data;
    }

    public void setData(Order data) {
        this.data = data;
    }
}
