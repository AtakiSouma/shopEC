package com.example.online_shop_app.Activity.detail;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.online_shop_app.API.Brand.BrandRepository;
import com.example.online_shop_app.API.Brand.BrandService;
import com.example.online_shop_app.API.Brand.Response.BrandResponse;
import com.example.online_shop_app.Activity.MainActivity;
import com.example.online_shop_app.Adapter.BrandAdapter;
import com.example.online_shop_app.Adapter.CategoryAdapter;
import com.example.online_shop_app.Fragment.HomeFragment;
import com.example.online_shop_app.HomeActivity;
import com.example.online_shop_app.Models.Brand;
import com.example.online_shop_app.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BrandService brandService;
    private BrandAdapter brandAdapter;
    private ImageView btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        fetchBrands();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToHome();
            }
        });


    }

    private void backToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void fetchBrands() {
        brandService = BrandRepository.callApiBrand(this, true);
        Call<BrandResponse> call = brandService.getBrand(1, 10);
        call.enqueue(new Callback<BrandResponse>() {
            @Override
            public void onResponse(Call<BrandResponse> call, Response<BrandResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BrandResponse brandResponse = response.body();
                    if (brandResponse.isSuccess()) {
                        List<Brand> brand = brandResponse.getData().getData();
                        brandAdapter = new BrandAdapter(brand);
                        recyclerView.setAdapter(brandAdapter);
                    } else {
                        Log.e("API fetch Brand Error", response.message());
                    }

                } else {
                    Log.e("API fetch Brand Error", response.message());
                }
            }

            @Override
            public void onFailure(Call<BrandResponse> call, Throwable t) {
                Log.e("API fetch Brand Error", "eror");

            }
        });

    }

    private void initView() {
        recyclerView = findViewById(R.id.brand_recycle_view);
        btn_back = findViewById(R.id.btn_back_to_home_v2);
    }
}