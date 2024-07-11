package com.example.online_shop_app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.online_shop_app.Activity.auth.LandingActivity;
import com.example.online_shop_app.Activity.auth.LoginActivity;
import com.example.online_shop_app.HomeActivity;
import com.example.online_shop_app.R;

public class SplashActivity extends AppCompatActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Check if the access token exists
        SharedPreferences sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("accessToken", "");
        String role_name = sharedPreferences.getString("role", "");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (getIntent().getExtras() != null) {
            // from notification
            String userId = getIntent().getExtras().getString("userId");
            Log.d("userId", userId);
            Intent intent = new Intent(this, OrderCompletedActivity.class);
            startActivity(intent);
            finish();


        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (accessToken != null && !accessToken.isEmpty()) {
                        if ("staff".equals(role_name)) {
                            navigateTOAdminManager();
                        } else {
                            navigateToHome();
                        }
                    } else {
                        navigateToLogin();
                    }
                    finish();

                }
            }, 2000);
        }


    }

    private void navigateToHome() {
        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    private void navigateToLogin() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void navigateTOAdminManager() {
        Intent intent = new Intent(SplashActivity.this, AdminActivity.class);
        startActivity(intent);
    }
}