package com.example.online_shop_app.Activity.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.online_shop_app.API.auth.AuthRepository;
import com.example.online_shop_app.API.auth.AuthService;
import com.example.online_shop_app.API.auth.Request.RegisterRequest;
import com.example.online_shop_app.API.auth.Request.VerificationRequest;
import com.example.online_shop_app.API.auth.Response.RegisterResponse;
import com.example.online_shop_app.API.auth.Response.VerificationResponse;
import com.example.online_shop_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private ImageView btn_back_to_landing, passwordIcon;
    private EditText edt_username, edt_email, edt_password, edt_confirm_password;
    private TextView link_to_login;
    private AppCompatButton signInBtn;
    private boolean passwordShowing = false;

    private final String REQUIRE = "Require";
    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        btn_back_to_landing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToLanding();
            }
        });
        link_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLogin();
            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
        if (!checkInput()) {
            return;
        }
        String email = edt_email.getText().toString();
        String password = edt_password.getText().toString();
        String username = edt_username.getText().toString();
        String confirm_password = edt_confirm_password.getText().toString();

        // send verificattion
        VerificationRequest verificationRequest = new VerificationRequest(email);
        authService = AuthRepository.login(this, true);
        Call<VerificationResponse> call = authService.verification(verificationRequest);
        call.enqueue(new Callback<VerificationResponse>() {
            @Override
            public void onResponse(Call<VerificationResponse> call, Response<VerificationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    VerificationResponse verificationResponse = response.body();
                    if (verificationResponse.isSuccess()) {
                        Intent intent = new Intent(RegisterActivity.this, SendOTPActivity.class);
                        intent.putExtra("email", email);
                        intent.putExtra("password", password);
                        intent.putExtra("username", username);
                        intent.putExtra("confirm_password", confirm_password);
                        intent.putExtra("verificationCode" , verificationResponse.getData().getVerificationCode());
                        startActivity(intent);
                    } else {
                        Log.e("Send verification", "Send verification failed: " + response.message());
                        Toast.makeText(RegisterActivity.this, "Api Error:: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("Login", "Login failed: " + response.message());
                    Toast.makeText(RegisterActivity.this, "Api Error:: " + response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<VerificationResponse> call, Throwable t) {
                Log.e("Send verification", "Send verification failed ");

                Toast.makeText(RegisterActivity.this, "Api Error ", Toast.LENGTH_SHORT).show();

            }
        });


//       RegisterRequest registerRequest = new RegisterRequest(username,email , password, confirm_password);
//
//
//        authService = AuthRepository.login(this, true);
//        Call<RegisterResponse> call = authService.register(registerRequest);
//        call.enqueue(new Callback<RegisterResponse>() {
//            @Override
//            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
//                if(response.isSuccessful() && response.body() != null){
//                    RegisterResponse registerResponse = response.body();
//                    if(registerResponse.isSuccess()){
//                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }else{
//                        Log.e("Login", "Login failed: " + response.message());
//                        Toast.makeText(RegisterActivity.this, "Api Error:: " + response.message(), Toast.LENGTH_SHORT).show();
//                    }
//                }else{
//                    Log.e("Login", "Login failed: " + response.message());
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<RegisterResponse> call, Throwable t) {
//                Log.e("Login", "API call failed", t);
//
//            }
//        });
    }

    private boolean checkInput() {
        if (TextUtils.isEmpty(edt_email.getText().toString())) {
            edt_email.setError(REQUIRE);
            return false;
        }
        if (TextUtils.isEmpty(edt_password.getText().toString())) {
            edt_password.setError(REQUIRE);
            return false;
        }

        if (TextUtils.isEmpty(edt_confirm_password.getText().toString())) {
            edt_confirm_password.setError(REQUIRE);
            return false;
        }

        if (TextUtils.isEmpty(edt_username.getText().toString())) {
            edt_username.setError(REQUIRE);
            return false;
        }
        return true;
    }

    private void toLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void backToLanding() {
        Intent intent = new Intent(RegisterActivity.this, LandingActivity.class);
        startActivity(intent);
    }

    private void initView() {
        btn_back_to_landing = findViewById(R.id.btn_back_to_landing);
        edt_email = findViewById(R.id.edt_email_register);
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password_register);
        edt_confirm_password = findViewById(R.id.edt_confirm_password);
        link_to_login = findViewById(R.id.link_to_login);
        signInBtn = findViewById(R.id.signInBtn);
    }
}