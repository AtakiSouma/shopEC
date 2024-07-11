package com.example.online_shop_app.API.Brand;

import android.content.Context;

import com.example.online_shop_app.API.ApiClient;
import com.example.online_shop_app.API.Category.CategoryService;

public class BrandRepository {
    public static BrandService callApiBrand(Context context, boolean useLocal) {
        return ApiClient.getClient(context, useLocal).create(BrandService.class);
    }
}
