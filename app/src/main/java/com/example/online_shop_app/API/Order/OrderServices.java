package com.example.online_shop_app.API.Order;


import com.example.online_shop_app.API.Order.Request.OrderRequest;
import com.example.online_shop_app.API.Order.Response.AllOrderResponse;
import com.example.online_shop_app.API.Order.Response.OrderResponse;
import com.example.online_shop_app.API.Order.Response.OrderUpdateResponse;
import com.example.online_shop_app.Models.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrderServices {
    @GET("order/{userId}")
    Call<OrderResponse> getOrder(@Path("userId") String userId);

    @GET("order")
    Call<AllOrderResponse> getAllOrder();

    @PUT("order/completed/{id}")
    Call<OrderUpdateResponse> updateCompletedOrder(@Path("id") String id);

    @PUT("order/canceled/{id}")
    Call<OrderUpdateResponse> updateCancelOrder(@Path("id") String id);
}
