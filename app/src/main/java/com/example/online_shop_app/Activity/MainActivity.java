package com.example.online_shop_app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.online_shop_app.Activity.auth.LandingActivity;
import com.example.online_shop_app.Activity.auth.LoginActivity;
import com.example.online_shop_app.Adapter.PopularListAdapter;
import com.example.online_shop_app.Domain.PopularDomain;
import com.example.online_shop_app.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterPopular;
    private RecyclerView recyclerViewPopular;
    ImageView btn_back_to_landing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initRecyclerview();
        bottom_navigation();
        btn_back_to_landing = findViewById(R.id.back_to_landing);
        btn_back_to_landing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LandingActivity.class));

            }
        });


    }

    private void bottom_navigation() {
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout cartBtn = findViewById(R.id.cartBtn);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });
    }

    private void initRecyclerview() {
        ArrayList<PopularDomain> items = new ArrayList<>();
        items.add(new PopularDomain("MacBook Pro 13 M2 chip", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus luctus urna sed urna ultricies \n" +
                "ac tempor dui sagittis. In condimentum facilisis porta. Sed nec diam eu diam mattis viverra. \n" +
                "Nulla fringilla, orci ac euismod semper, magna diam porttitor mauris, quis sollicitudin sapien \n" +
                "justo in libero. Vestibulum mollis mauris enim. Morbi euismod magna ac lorem rutrum elementum. \n" +
                "Donec viverra auctor lobortis. Pellentesque eu est a nulla placerat dignissim. Morbi a enim in \n" +
                "magna semper bibendum. Etiam scelerisque, nunc ac egestas consequat, odio nibh euismod nulla, \n" +
                "eget auctor orci nibh vel nisi.\n", "pic1", 15, 4, 500));
        items.add(new PopularDomain("Ps-5 Digital", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus luctus urna sed urna ultricies \n" +
                "ac tempor dui sagittis. In condimentum facilisis porta. Sed nec diam eu diam mattis viverra. \n" +
                "Nulla fringilla, orci ac euismod semper, magna diam porttitor mauris, quis sollicitudin sapien \n" +
                "justo in libero. Vestibulum mollis mauris enim. Morbi euismod magna ac lorem rutrum elementum. \n" +
                "Donec viverra auctor lobortis. Pellentesque eu est a nulla placerat dignissim. Morbi a enim in \n" +
                "magna semper bibendum. Etiam scelerisque, nunc ac egestas consequat, odio nibh euismod nulla, \n" +
                "eget auctor orci nibh vel nisi.\n", "pic2", 10, 4.5, 450));
        items.add(new PopularDomain("Iphone 14", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus luctus urna sed urna ultricies \n" +
                "ac tempor dui sagittis. In condimentum facilisis porta. Sed nec diam eu diam mattis viverra. \n" +
                "Nulla fringilla, orci ac euismod semper, magna diam porttitor mauris, quis sollicitudin sapien \n" +
                "justo in libero. Vestibulum mollis mauris enim. Morbi euismod magna ac lorem rutrum elementum. \n" +
                "Donec viverra auctor lobortis. Pellentesque eu est a nulla placerat dignissim. Morbi a enim in \n" +
                "magna semper bibendum. Etiam scelerisque, nunc ac egestas consequat, odio nibh euismod nulla, \n" +
                "eget auctor orci nibh vel nisi.\n", "pic3", 13, 4.2, 800));

        recyclerViewPopular = findViewById(R.id.view1);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        adapterPopular = new PopularListAdapter(items);
        recyclerViewPopular.setAdapter(adapterPopular);
    }
}