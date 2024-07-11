package com.example.online_shop_app.API.Order.Response;

import com.example.online_shop_app.API.Category.Response.CategoryResponse;
import com.example.online_shop_app.Models.Order;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderResponse {
    private boolean success;
    private int status;
    @SerializedName("data")
    private List<Order> data;

    public OrderResponse(boolean success, int status, List<Order> data) {
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
