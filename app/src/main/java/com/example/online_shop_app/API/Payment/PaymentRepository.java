package com.example.online_shop_app.API.Payment;

import android.content.Context;

import com.example.online_shop_app.API.ApiClient;

public class PaymentRepository {
    public static PaymentService payment(Context context, boolean useLocal) {
        return ApiClient.getClient(context, useLocal).create(PaymentService.class);
    }
}
