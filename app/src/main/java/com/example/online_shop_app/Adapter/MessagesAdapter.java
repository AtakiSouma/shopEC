package com.example.online_shop_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.online_shop_app.Models.Messages;
import com.example.online_shop_app.R;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {

    private List<Messages> messages;
    private String currentUserId;
    private Context context;

    public MessagesAdapter(List<Messages> messages, String currentUserId, Context context) {
        this.messages = messages;
        this.currentUserId = currentUserId;
        this.context = context;
    }

    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_message_recycle_view, parent, false);
        return new MessagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder holder, int position) {
        Messages message = messages.get(position);
        if (message.getSenderId().equals(currentUserId)) {
            holder.leftChatLayout.setVisibility(View.GONE);
            holder.rightChatLayout.setVisibility(View.VISIBLE);
            holder.rightChatTextView.setText(message.getText());
        } else {
            holder.rightChatLayout.setVisibility(View.GONE);
            holder.leftChatLayout.setVisibility(View.VISIBLE);
            holder.leftChatTextView.setText(message.getText());
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    public class MessagesViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout leftChatLayout;
        private LinearLayout rightChatLayout;
        private TextView leftChatTextView;
        private TextView rightChatTextView;

        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);
            leftChatLayout = itemView.findViewById(R.id.left_chat_layout);
            rightChatLayout = itemView.findViewById(R.id.right_chat_layout);
            leftChatTextView = itemView.findViewById(R.id.left_chat_textview);
            rightChatTextView = itemView.findViewById(R.id.right_chat_textview);
        }
    }
}
