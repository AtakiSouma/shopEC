package com.example.online_shop_app.API.Order;

import android.content.Context;

import com.example.online_shop_app.API.ApiClient;
import com.example.online_shop_app.API.Payment.PaymentService;
import com.example.online_shop_app.Models.Order;

public class OrderRepository {
    public static OrderServices callApiOrder(Context context, boolean useLocal) {
        return ApiClient.getClient(context, useLocal).create(OrderServices.class);
    }
}
