package com.example.online_shop_app.Activity.auth;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import com.example.online_shop_app.API.auth.Response.RegisterResponse;
import com.example.online_shop_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendOTPActivity extends AppCompatActivity {
    private EditText otpET1, otpET2, otpET3, otpET4;
    private TextView resendBtn;
    private AppCompatButton verifyOTP;
    // true after 60s
    private boolean resendEnabled = false;
    private int resendTime = 60;
    private int seletedETPosition = 0;
    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_send_otpactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        otpET1.addTextChangedListener(textWatcher);
        otpET2.addTextChangedListener(textWatcher);
        otpET3.addTextChangedListener(textWatcher);
        otpET4.addTextChangedListener(textWatcher);

        // get data from intent register
        final String email = getIntent().getStringExtra("email");
        final String password = getIntent().getStringExtra("password");
        final String username = getIntent().getStringExtra("username");
        final String confirm_password = getIntent().getStringExtra("confirm_password");
        final String verificationCodeFromEmail = getIntent().getStringExtra("verificationCode");


        // default open in otpET1
        showKeyboard(otpET1);
        // start CountDown Timer
        startCountDownTimer();
        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resendEnabled) {
                    // handle resend code

                    startCountDownTimer();

                }
            }
        });
        verifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String generateKey = otpET1.getText().toString() + otpET2.getText().toString() + otpET3.getText().toString() + otpET4.getText().toString();
                if (generateKey.length() == 4 && generateKey.equals(verificationCodeFromEmail)) {
                    RegisterRequest registerRequest = new RegisterRequest(username, email, password, confirm_password);
                    authService = AuthRepository.login(SendOTPActivity.this, true);
                    Call<RegisterResponse> call = authService.register(registerRequest);
                    call.enqueue(new Callback<RegisterResponse>() {
                        @Override
                        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                RegisterResponse registerResponse = response.body();
                                if (registerResponse.isSuccess()) {
                                    Intent intent = new Intent(SendOTPActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Log.e("Login", "Login failed: " + response.message());
                                    Toast.makeText(SendOTPActivity.this, "Api Error:: " + response.message(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Log.e("Login", "Login failed: " + response.message());

                            }
                        }

                        @Override
                        public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.e("Login", "API call failed", t);

                        }
                    });
                }
            }
        });


    }

    private void showKeyboard(EditText otpET) {
        otpET.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otpET, InputMethodManager.SHOW_IMPLICIT);

    }

    private void startCountDownTimer() {
        resendEnabled = false;
        resendBtn.setTextColor(Color.parseColor("#99000000"));
        new CountDownTimer(resendTime * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                resendBtn.setText("Resend Code * (" + millisUntilFinished / 1000 + ")");

            }

            @Override
            public void onFinish() {
                resendEnabled = true;
                resendBtn.setText("Resend Code");
                resendBtn.setTextColor(getResources().getColor(R.color.primary));

            }
        }.start();
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0) {
                if (seletedETPosition == 0) {
                    seletedETPosition = 1;
                    showKeyboard(otpET2);

                } else if (seletedETPosition == 1) {
                    seletedETPosition = 2;
                    showKeyboard(otpET3);
                } else if (seletedETPosition == 2) {
                    seletedETPosition = 3;
                    showKeyboard(otpET4);
                }
            }
        }

    };

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            if (seletedETPosition == 3) {
                seletedETPosition = 2;
                showKeyboard(otpET3);
            } else if (seletedETPosition == 2) {
                seletedETPosition = 1;
                showKeyboard(otpET2);
            } else if (seletedETPosition == 1) {
                seletedETPosition = 0;
                showKeyboard(otpET1);
            }
            return true;
        } else {
            return super.onKeyUp(keyCode, event);

        }
    }

    private void initView() {
        otpET1 = findViewById(R.id.otpET1);
        otpET2 = findViewById(R.id.otpET2);
        otpET3 = findViewById(R.id.otpET3);
        otpET4 = findViewById(R.id.otpET4);
        resendBtn = findViewById(R.id.resend_btn);
        verifyOTP = findViewById(R.id.btn_verify_otp);

    }
}