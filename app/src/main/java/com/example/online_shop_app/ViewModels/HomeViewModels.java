package com.example.online_shop_app.ViewModels;


import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.online_shop_app.API.Category.CategoryRepository;
import com.example.online_shop_app.API.Category.CategoryService;
import com.example.online_shop_app.API.Category.Response.CategoryResponse;
import com.example.online_shop_app.API.Product.ProductRRepository;
import com.example.online_shop_app.API.Product.ProductService;
import com.example.online_shop_app.API.Product.Response.ProductResponse;
import com.example.online_shop_app.Models.Category;
import com.example.online_shop_app.Models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModels extends ViewModel {
    private MutableLiveData<List<Category>> categories;
    private MutableLiveData<List<Product>> products;
    private CategoryRepository categoryRepository;
    private ProductRRepository productRepository;
    private Context context;
    private boolean useLocal;

    public HomeViewModels(Context context, boolean useLocal) {
        this.context = context;
        this.useLocal = useLocal;
        categoryRepository = new CategoryRepository();
        productRepository = new ProductRRepository();
        categories = new MutableLiveData<>();
        products = new MutableLiveData<>();
    }

    public LiveData<List<Category>> getCategories(int page, int limit) {
        if (categories.getValue() == null || categories.getValue().isEmpty()) {
            fetchCategories(page, limit);
        }
        return categories;
    }

    public LiveData<List<Product>> getProducts(int page, int limit) {
        if (products.getValue() == null || products.getValue().isEmpty()) {
            fetchProducts(page, limit);
        }
        return products;

    }

    private void fetchProducts(int page, int limit) {
        ProductService productService = productRepository.callApiProduct(context, useLocal);
        Call<ProductResponse> call = productService.getProduct(page, limit);
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    products.setValue(response.body().getData().getData());
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.d("API error" , "Error from :: get product");
            }
        });
    }

    private void fetchCategories(int page, int limit) {
        CategoryService categoryService = categoryRepository.callApiCategory(context, useLocal);
        Call<CategoryResponse> call = categoryService.getCategories(page, limit);
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categories.setValue(response.body().getData().getData());
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                // Handle error
                Log.d("API error" , "Error from :: get category");

            }
        });

    }


}
