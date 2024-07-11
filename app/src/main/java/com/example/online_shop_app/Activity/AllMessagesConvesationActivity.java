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

import com.example.online_shop_app.API.Chat.ChatRepository;
import com.example.online_shop_app.API.Chat.ChatServices;
import com.example.online_shop_app.API.Chat.Response.CreateConversationResponse;
import com.example.online_shop_app.API.Chat.Response.ListConversationResponse;
import com.example.online_shop_app.Adapter.ConversationAdapter;
import com.example.online_shop_app.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllMessagesConvesationActivity extends AppCompatActivity {
    private ImageView back_btn;
    private ChatServices chatServices;
    private RecyclerView recyclerViewConversation;
    private ConversationAdapter conversationAdapter;
    private List<ListConversationResponse.Data> conversationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_messages_convesation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        recyclerViewConversation.setLayoutManager(new LinearLayoutManager(this));
        conversationAdapter = new ConversationAdapter(conversationList,this);
        recyclerViewConversation.setAdapter(conversationAdapter);
        fetchConversation();
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void fetchConversation() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("auth", Context.MODE_PRIVATE);
        String userId_Buyer = sharedPreferences.getString("userId", "");
        chatServices = ChatRepository.callApiForChat(this, true);
        Call<ListConversationResponse> call = chatServices.getConversationByUserId(userId_Buyer);
        call.enqueue(new Callback<ListConversationResponse>() {
            @Override
            public void onResponse(Call<ListConversationResponse> call, Response<ListConversationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    ListConversationResponse conversationResponse = response.body();
                    if (conversationResponse.isSuccess()) {
                        conversationList.clear();
                        conversationList.addAll(conversationResponse.getData());
                        conversationAdapter.notifyDataSetChanged();
                    }


                } else {
                    Log.e("API_CALL", "Failure: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ListConversationResponse> call, Throwable t) {
                Log.e("API_CALL", "Error: " + t.getMessage());


            }
        });


    }

    private void initView() {
        back_btn = findViewById(R.id.back_btn);
        recyclerViewConversation = findViewById(R.id.all_conversation_recycle_view);
    }
}