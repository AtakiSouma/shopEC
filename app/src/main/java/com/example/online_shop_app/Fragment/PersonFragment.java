package com.example.online_shop_app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.online_shop_app.Activity.auth.LandingActivity;
import com.example.online_shop_app.Activity.auth.LoginActivity;
import com.example.online_shop_app.R;
import com.example.online_shop_app.Utils.JWTUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;


public class PersonFragment extends Fragment {

    private AppCompatButton btn_logout;

    public PersonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        btn_logout = view.findViewById(R.id.btn_logout);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("auth", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("accessToken", "");
        if (!accessToken.isEmpty()) {
            try {
                JWTUtils.decoded(accessToken);
            } catch (Exception e) {
                Log.e("JWTUtils", "Failed to decode JWT", e);
            }
        } else {
            Log.d("JWTUtils", "No access token found in SharedPreferences");
        }


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutFunction();
            }
        });

        return view;
    }

    private void logoutFunction() {
        FirebaseMessaging.getInstance().deleteToken().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    clearAuthData();
                    gotoLogin();
                }
            }

        });
    }


    // -------------------------------------------------
    //// *************** function ********************
    // -------------------------------------------------
    private void gotoLogin() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void clearAuthData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("auth", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("accessToken");
        editor.remove("userId");
        editor.remove("role");
        editor.apply();
    }


}