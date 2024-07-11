package com.example.online_shop_app.API.Order.Response;

import com.example.online_shop_app.Models.Order;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllOrderResponse {
    private boolean success;
    private int status;
    @SerializedName("data")
    private List<Order> data;

    public AllOrderResponse(boolean success, int status, List<Order> data) {
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

    public List<Order> getData() {
        return data;
    }

    public void setData(List<Order> data) {
        this.data = data;
    }
}
