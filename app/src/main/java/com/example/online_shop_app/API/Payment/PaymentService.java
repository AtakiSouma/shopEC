package com.example.online_shop_app.API.Payment;

import com.example.online_shop_app.API.Payment.Request.OrderRequest;
import com.example.online_shop_app.API.Payment.Request.PaymentRequest;
import com.example.online_shop_app.API.Payment.Response.OrderResponse;
import com.example.online_shop_app.API.Payment.Response.PaymentResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PaymentService {
    @POST("payment")
    Call<PaymentResponse> makePayment(@Body PaymentRequest request);

    @POST("order")
    Call<OrderResponse> createOrder(@Body OrderRequest request);
}
