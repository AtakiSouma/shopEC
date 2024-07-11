package com.example.online_shop_app.API.Chat;

import com.example.online_shop_app.API.Chat.Request.CreateConversationRequest;
import com.example.online_shop_app.API.Chat.Request.CreateMessagesRequest;
import com.example.online_shop_app.API.Chat.Response.CreateConversationResponse;
import com.example.online_shop_app.API.Chat.Response.CreateMessageResponse;
import com.example.online_shop_app.API.Chat.Response.GetListMessageResponse;
import com.example.online_shop_app.API.Chat.Response.ListConversationResponse;
import com.example.online_shop_app.API.Order.Response.OrderResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChatServices {
    @POST("conversation")
    Call<CreateConversationResponse> createConversation(@Body CreateConversationRequest createConversationRequest);

    @GET("conversation/{userId}")
    Call<ListConversationResponse> getConversationByUserId(@Path("userId") String userId);

    @GET("message/{conversationId}")
    Call<GetListMessageResponse> getListMessage(@Path("conversationId") String conversationId);
    @POST("message")
    Call<CreateMessageResponse> createMessages(@Body CreateMessagesRequest createMessagesRequest);


}
