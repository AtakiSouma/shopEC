package com.example.online_shop_app.API.Chat;

import android.content.Context;

import com.example.online_shop_app.API.ApiClient;
import com.example.online_shop_app.API.Order.OrderServices;

public class ChatRepository {
    public static ChatServices callApiForChat(Context context, boolean useLocal) {
        return ApiClient.getClient(context, useLocal).create(ChatServices.class);
    }
}
