package com.example.online_shop_app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.online_shop_app.API.Order.OrderRepository;
import com.example.online_shop_app.API.Order.OrderServices;
import com.example.online_shop_app.API.Order.Request.OrderRequest;
import com.example.online_shop_app.API.Order.Response.OrderResponse;
import com.example.online_shop_app.API.auth.Response.LoginResponse;
import com.example.online_shop_app.Adapter.OrderAdapter;
import com.example.online_shop_app.HomeActivity;
import com.example.online_shop_app.Models.Order;
import com.example.online_shop_app.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderCompletedActivity extends AppCompatActivity {
    private RecyclerView recycle_view_order_completed;
    private OrderAdapter orderAdapter;
    private List<Order> orderList = new ArrayList<>();
    private OrderRepository orderRepository;
    private OrderServices orderServices;
    private OrderRequest orderRequest;
    private ImageView btn_back_to_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_completed);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        recycle_view_order_completed.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter(orderList);
        recycle_view_order_completed.setAdapter(orderAdapter);
        btn_back_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToCartFragment();
            }
        });
        fetchOrders();

    }

    private void backToCartFragment() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void fetchOrders() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("auth", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");
        orderServices = orderRepository.callApiOrder(this, true);
        orderRequest = new OrderRequest(userId);

        Call<OrderResponse> call = orderServices.getOrder(userId);
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    OrderResponse orderResponse = response.body();
                    if (orderResponse.isSuccess()) {
                        orderList.clear();
                        orderList.addAll(orderResponse.getData());
                        orderAdapter.notifyDataSetChanged();
                    }

                } else {
                    Log.e("Errror API :::", response.message());
                }

            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.e("Errror API :::", "Failed fetch api");

            }
        });

    }

    private void initView() {
        recycle_view_order_completed = findViewById(R.id.recycle_view_order_completed);
        btn_back_to_home = findViewById(R.id.btn_back_to_home);
    }
}