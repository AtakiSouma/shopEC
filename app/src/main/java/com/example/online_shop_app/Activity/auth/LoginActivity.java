package com.example.online_shop_app.Activity.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.online_shop_app.API.ApiClient;
import com.example.online_shop_app.API.auth.AuthRepository;
import com.example.online_shop_app.API.auth.AuthService;
import com.example.online_shop_app.API.auth.Request.LoginRequest;
import com.example.online_shop_app.API.auth.Request.RegisterRequest;
import com.example.online_shop_app.API.auth.Response.LoginResponse;
import com.example.online_shop_app.Activity.AdminActivity;
import com.example.online_shop_app.Activity.MainActivity;
import com.example.online_shop_app.HomeActivity;
import com.example.online_shop_app.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private boolean passwordShowing = false;
    private EditText edt_email, edt_password;
    private final String REQUIRE = "Require";
    private AppCompatButton btn_signIn;
    private ImageView link_to_back, passwordIcon;
    private TextView link_to_signUp;
    private AuthService authService;
    private RelativeLayout signInWithGG;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private String token;
    private GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        // get token
        getFCMToken();
        // login google
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.wed_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
        signInWithGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogle();
            }
        });

        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordShowing) {
                    passwordShowing = false;
                    edt_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.eye);
                } else {
                    passwordShowing = true;
                    edt_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.invisible);
                }
                edt_password.setSelection(edt_password.length());
            }
        });
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        link_to_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linkToBackScreen();
            }
        });
        link_to_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linkToSignUpScreen();
            }
        });
    }

    private void linkToSignUpScreen() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    private void linkToBackScreen() {
        Intent intent = new Intent(LoginActivity.this, LandingActivity.class);
        startActivity(intent);
        finish();
    }

    private void signInWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account.getIdToken());
                Log.d("idTOken", account.getIdToken());
                Log.d("account name", account.getDisplayName());

            } catch (Exception e) {
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    private void signIn() {
        if (!checkInput()) {
            return;
        }
        String email = edt_email.getText().toString();
        String password = edt_password.getText().toString();
        LoginRequest loginRequest = new LoginRequest(email, password, token);
        authService = AuthRepository.login(this, true);
        Call<LoginResponse> call = authService.login(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse.isSuccess()) {
                        String accessToken = loginResponse.getData().getAccessToken();
                        String userID = loginResponse.getData().getUser().getId();
                        String role = loginResponse.getData().getUser().getRoleName();
                        Log.d("Login", "Login successful! Access token: " + accessToken);
                        Log.d("userID", "Login successful! userId" + userID);
                        // Save access token to SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("auth", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("accessToken", accessToken);
                        editor.putString("userId", userID);
                        editor.putString("role", role);

                        editor.apply();

                        // end
                        if("staff".equals(role)){
                            gotoAdmin();
                        }else{
                            gotoHome();
                        }

                    } else {
                        Log.e("Login", "Login failed: " + response.message());
                    }
                } else {
                    Log.e("Login", "Login failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("Login", "API call failed", t);

            }
        });

    }

    private boolean checkInput() {
        // email
        if (TextUtils.isEmpty(edt_email.getText().toString())) {
            edt_email.setError(REQUIRE);
            return false;
        }

        // passworrd
        if (TextUtils.isEmpty(edt_password.getText().toString())) {
            edt_password.setError(REQUIRE);
            return false;
        }
        return true;
    }

    private void initView() {
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        link_to_back = findViewById(R.id.link_to_landing);
        link_to_signUp = findViewById(R.id.link_to_signUp);
        btn_signIn = findViewById(R.id.btn_signIn);
        passwordIcon = findViewById(R.id.passwordIcon);
        signInWithGG = findViewById(R.id.signInWithGGLogin);
    }

    private void getFCMToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                token = task.getResult();
                Log.i("My RefreshToken", token);
            }
        });
    }
    private  void gotoHome(){
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
    private  void gotoAdmin(){
        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
        startActivity(intent);
        finish();
    }
}