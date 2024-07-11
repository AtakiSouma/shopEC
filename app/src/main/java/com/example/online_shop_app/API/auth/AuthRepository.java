package com.example.online_shop_app.API.auth;

import android.content.Context;

import com.example.online_shop_app.API.ApiClient;

public class AuthRepository {
    public static  AuthService login(Context context , boolean useLocal){
            return ApiClient.getClient(context,useLocal).create(AuthService.class);
    }

}
