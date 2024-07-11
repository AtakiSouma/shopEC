package com.example.online_shop_app.API.Category;

import android.content.Context;

import com.example.online_shop_app.API.ApiClient;
import com.example.online_shop_app.API.auth.AuthService;

public class CategoryRepository {
    public static CategoryService callApiCategory(Context context , boolean useLocal){
        return ApiClient.getClient(context,useLocal).create(CategoryService.class);
    }
}
