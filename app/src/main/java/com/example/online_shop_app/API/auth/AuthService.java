package com.example.online_shop_app.API.auth;

import com.example.online_shop_app.API.auth.Request.LoginRequest;
import com.example.online_shop_app.API.auth.Request.RegisterRequest;
import com.example.online_shop_app.API.auth.Request.VerificationRequest;
import com.example.online_shop_app.API.auth.Response.LoginResponse;
import com.example.online_shop_app.API.auth.Response.RegisterResponse;
import com.example.online_shop_app.API.auth.Response.VerificationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("auth/register")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);

    @POST("auth/verification")
    Call<VerificationResponse> verification(@Body VerificationRequest verificationRequest);
}
