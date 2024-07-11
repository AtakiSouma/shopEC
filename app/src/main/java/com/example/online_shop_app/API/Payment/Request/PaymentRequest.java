package com.example.online_shop_app.API.Payment.Request;

public class PaymentRequest {
    private  String order_data;

    public PaymentRequest(String order_data) {
        this.order_data = order_data;
    }

    public String getOrder_data() {
        return order_data;
    }

    public void setOrder_data(String order_data) {
        this.order_data = order_data;
    }
}
