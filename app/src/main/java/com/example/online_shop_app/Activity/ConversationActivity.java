package com.example.online_shop_app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.online_shop_app.API.Chat.ChatRepository;
import com.example.online_shop_app.API.Chat.ChatServices;
import com.example.online_shop_app.API.Chat.Request.CreateMessagesRequest;
import com.example.online_shop_app.API.Chat.Response.CreateMessageResponse;
import com.example.online_shop_app.API.Chat.Response.GetListMessageResponse;
import com.example.online_shop_app.API.Chat.Response.ListConversationResponse;
import com.example.online_shop_app.Adapter.MessagesAdapter;
import com.example.online_shop_app.Models.Messages;
import com.example.online_shop_app.R;
import com.example.online_shop_app.Utils.SocketManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConversationActivity extends AppCompatActivity {
    private ImageView back_btn;
    private TextView admin_username_of_other;
    private String conversationId, groupTitle, fullName, avatar, email, receiverId;
    private RecyclerView chat_recycler_view;
    private ChatServices chatServices;
    private List<Messages> messages;
    private MessagesAdapter messagesAdapter;
    private Socket socket;
    private EditText chatMessageInput;
    private ImageButton messageSendBtn;
    private  ImageView avatar_other_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_conversation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        retrieveDataFromIntent();
        setUpDataForChatConversation();
        // set up currentUserId / conversationID
        SharedPreferences sharedPreferences = this.getSharedPreferences("auth", Context.MODE_PRIVATE);
        String currentUserId = sharedPreferences.getString("userId", "");
        // set up recycle view
        messages = new ArrayList<>();
        messagesAdapter = new MessagesAdapter(messages, currentUserId, this);
        chat_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        chat_recycler_view.setAdapter(messagesAdapter);

        //**********************
        // set up socket
        socket = SocketManager.getSocket();

        socket.connect();
        socket.emit("addUser", currentUserId);

        //***********


        fetchMessages();
        socket.on("getMessage", onGetMessage);


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToPreviousActivity();
            }
        });
        messageSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(currentUserId);

            }
        });
    }

    private void sendMessage(String senderId) {
        String messageText = chatMessageInput.getText().toString().trim();
        if (!messageText.isEmpty()) {
            Messages newMessage = new Messages();
            newMessage.setSenderId(senderId);
            newMessage.setReceiverId(receiverId);
            newMessage.setText(messageText);
            messages.add(newMessage);

            messagesAdapter.notifyItemInserted(messages.size() - 1);
            chat_recycler_view.scrollToPosition(messages.size() - 1);
            chatServices = ChatRepository.callApiForChat(this, true);
            // request
            CreateMessagesRequest request = new CreateMessagesRequest(senderId, conversationId, messageText);
            // receiver iod
            if (receiverId.isEmpty()) {
                receiverId = "6b69954a-2f45-430d-b04b-fdbec67a1939";

            }

            Call<CreateMessageResponse> call = chatServices.createMessages(request);
            call.enqueue(new Callback<CreateMessageResponse>() {
                @Override
                public void onResponse(Call<CreateMessageResponse> call, Response<CreateMessageResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        CreateMessageResponse createMessageResponse = response.body();
                        if (createMessageResponse.isSuccess()) {
                            // Emit the message via Socket.IO
                            try {
                                JSONObject message = new JSONObject();
                                message.put("senderId", senderId);
                                message.put("receiverId", receiverId); // Replace with actual receiver ID
                                message.put("text", messageText);
                                socket.emit("sendMessage", message);


                                chatMessageInput.setText(""); // Clear the input field
                                messagesAdapter.notifyDataSetChanged();
                                messagesAdapter.notifyItemInserted(messages.size() - 1);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    } else {
                        Log.e("API_CALL", "Failure: " + response.code());

                    }
                }

                @Override
                public void onFailure(Call<CreateMessageResponse> call, Throwable t) {
                    Log.e("API_CALL", "Error: " + t.getMessage());

                }
            });
        }


    }

    private void fetchMessages() {
        chatServices = ChatRepository.callApiForChat(this, true);
        Call<GetListMessageResponse> call = chatServices.getListMessage(conversationId);
        call.enqueue(new Callback<GetListMessageResponse>() {
            @Override
            public void onResponse(Call<GetListMessageResponse> call, Response<GetListMessageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GetListMessageResponse messagesResponse = response.body();
                    if (messagesResponse.isSuccess()) {
                        messages.addAll(messagesResponse.getData());
                        messagesAdapter.notifyDataSetChanged();
                        chat_recycler_view.scrollToPosition(messages.size() - 1);

                    }

                } else {
                    Log.e("API_CALL", "Failure: " + response.code());

                }
            }

            @Override
            public void onFailure(Call<GetListMessageResponse> call, Throwable t) {

            }
        });

    }

    private final Emitter.Listener onGetMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {

                        String senderId = data.getString("senderId");
                        String receiverId = data.getString("receiverId");
                        String text = data.getString("text");
                        String images = data.optString("images", null);

                        Messages message = new Messages();
                        message.setSenderId(senderId);
                        message.setReceiverId(receiverId);
                        message.setText(text);
                        message.setImages(images);
                        messages.add(message);
                        Log.d("SOCKET", "Message added: " + message.getText());
                        messagesAdapter.notifyItemInserted(messages.size() - 1);
                        chat_recycler_view.scrollToPosition(messages.size() - 1);
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }
            });
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
        socket.off("getMessage", onGetMessage);
    }

    private void setUpDataForChatConversation() {
        if (fullName != null) {
            admin_username_of_other.setText(fullName);
        }
        if(avatar !=null){
            Glide.with(this).load(avatar).into(avatar_other_user);
        }
    }

    private void retrieveDataFromIntent() {
        // Retrieve data from the Intent
        Intent intent = getIntent();
        conversationId = intent.getStringExtra("conversationId");
        groupTitle = intent.getStringExtra("groupTitle");
        fullName = intent.getStringExtra("fullName");
        avatar = intent.getStringExtra("avatar");
        email = intent.getStringExtra("email");
        receiverId = intent.getStringExtra("receiverId");
        Log.d("receiverId", receiverId);
    }

    private void backToPreviousActivity() {
        finish();

    }


    private void initView() {
        back_btn = findViewById(R.id.back_btn);
        admin_username_of_other = findViewById(R.id.admin_username_of_other);
        chat_recycler_view = findViewById(R.id.chat_recycler_view);
        chatMessageInput = findViewById(R.id.chat_message_input);
        messageSendBtn = findViewById(R.id.message_send_btn);
        avatar_other_user = findViewById(R.id.avatar_other_user);
    }

}