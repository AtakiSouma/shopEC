package com.example.online_shop_app.Activity;

import android.content.Intent;
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
import com.example.online_shop_app.API.Order.Response.AllOrderResponse;
import com.example.online_shop_app.API.Order.Response.OrderResponse;
import com.example.online_shop_app.Adapter.OrderManagerAdapter;
import com.example.online_shop_app.Models.Order;
import com.example.online_shop_app.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity {
    private RecyclerView list_order_recycleView;
    private OrderManagerAdapter orderManagerAdapter;
    private List<Order> orderList = new ArrayList<>();
    private OrderServices orderServices;
    private ImageView gotoMesage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        list_order_recycleView.setLayoutManager(new LinearLayoutManager(this));
        orderManagerAdapter = new OrderManagerAdapter(orderList, this);
        list_order_recycleView.setAdapter(orderManagerAdapter);
        fetchAllOrders();
        gotoMesage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoConversation();
            }
        });


    }

    private void gotoConversation() {
        Intent intent = new Intent(this, AllMessagesConvesationActivity.class);
        startActivity(intent);
        finish();
    }

    private void fetchAllOrders() {
        orderServices = OrderRepository.callApiOrder(this, true);
        Call<AllOrderResponse> call = orderServices.getAllOrder();
        call.enqueue(new Callback<AllOrderResponse>() {
            @Override
            public void onResponse(Call<AllOrderResponse> call, Response<AllOrderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AllOrderResponse orderResponse = response.body();
                    if (orderResponse.isSuccess()) {
                        orderList.clear();
                        orderList.addAll(orderResponse.getData());
                        orderManagerAdapter.notifyDataSetChanged();

                    } else {
                        Log.e("Errror API :::", response.message());

                    }
                } else {
                    Log.e("Errror API :::", response.message());

                }
            }

            @Override
            public void onFailure(Call<AllOrderResponse> call, Throwable t) {
                Log.e("Errror API :::", "Failed fetch api");

            }
        });

    }

    private void initView() {
        list_order_recycleView = findViewById(R.id.list_order_recycleView);
        gotoMesage = findViewById(R.id.gotoMesage)
        ;
    }
}