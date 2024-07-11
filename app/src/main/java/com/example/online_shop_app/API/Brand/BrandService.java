package com.example.online_shop_app.API.Brand;

import com.example.online_shop_app.API.Brand.Response.BrandResponse;
import com.example.online_shop_app.API.Category.Response.CategoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BrandService {
    @GET("brand")
    Call<BrandResponse> getBrand(
            @Query("page") int page,
            @Query("limit") int limit
    );
}
