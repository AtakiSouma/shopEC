package com.example.online_shop_app.API.Product;

import android.content.Context;

import com.example.online_shop_app.API.ApiClient;

public class ProductRRepository {
    public static ProductService callApiProduct(Context context , boolean useLocal){
        return ApiClient.getClient(context,useLocal).create(ProductService.class);
    }
}
