package com.example.online_shop_app.API.Product;

import com.example.online_shop_app.API.Category.Response.CategoryResponse;
import com.example.online_shop_app.API.Product.Response.ProductDetailResponse;
import com.example.online_shop_app.API.Product.Response.ProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductService {
    @GET("product")
    Call<ProductResponse> getProduct(
            @Query("page") int page,
            @Query("limit") int limit
    );
    @GET("product/{id}")
    Call<ProductDetailResponse> getProductById(@Path("id") String productId);

}
