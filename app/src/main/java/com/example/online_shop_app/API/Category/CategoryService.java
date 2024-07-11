package com.example.online_shop_app.API.Category;

import com.example.online_shop_app.API.Category.Response.CategoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CategoryService {
    @GET("category")
    Call<CategoryResponse> getCategories(
            @Query("page") int page,
            @Query("limit") int limit
    );
}
