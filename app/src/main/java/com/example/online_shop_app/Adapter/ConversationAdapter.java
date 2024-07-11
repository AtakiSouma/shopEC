package com.example.online_shop_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.online_shop_app.API.Chat.Response.CreateConversationResponse;
import com.example.online_shop_app.API.Chat.Response.ListConversationResponse;
import com.example.online_shop_app.Activity.ConversationActivity;
import com.example.online_shop_app.Activity.ProductDetailActivity;
import com.example.online_shop_app.Models.Brand;
import com.example.online_shop_app.R;

import java.util.List;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder> {
    private List<ListConversationResponse.Data> conversationList;
    private Context context;

    public ConversationAdapter(List<ListConversationResponse.Data> conversationList, Context context) {
        this.conversationList = conversationList;
        this.context = context;

    }

    public List<ListConversationResponse.Data> getConversationList() {
        return conversationList;
    }

    public void setConversationList(List<ListConversationResponse.Data> conversationList) {
        this.conversationList = conversationList;
    }

    @NonNull
    @Override
    public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ConversationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
        String currentUserID = sharedPreferences.getString("userId", "");
        ListConversationResponse.Data conversation = conversationList.get(position);
        if (conversation.getMembers() != null && !conversation.getMembers().isEmpty()) {
            ListConversationResponse.Data.Member otherMember = null;
            for (ListConversationResponse.Data.Member member : conversation.getMembers()) {
                ListConversationResponse.Data.Member.User user = member.getUser();
                if (user != null && !user.getId().equals(currentUserID)) {
                    otherMember = member;
                    break;
                }
            }
            // If we found another member, add their aa to the intent
            if (otherMember != null) {
                ListConversationResponse.Data.Member.User user = otherMember.getUser();
                if (user != null) {
                    holder.name_other_user.setText(user.getFull_name());
                    Glide.with(holder.itemView.getContext()).load(user.getAvatar()).into(holder.avatar_other_user);
                    holder.lastMessages.setText(conversation.getLastMessage());
                }
            }
        }

        holder.avatar_other_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ConversationActivity.class);
                intent.putExtra("conversationId", conversation.getId());
                intent.putExtra("groupTitle", conversation.getGroupTitle());
                if (conversation.getMembers() != null && !conversation.getMembers().isEmpty()) {
                    ListConversationResponse.Data.Member otherMember = null;

                    // Iterate through the members to find a member with a different user ID
                    for (ListConversationResponse.Data.Member member : conversation.getMembers()) {
                        ListConversationResponse.Data.Member.User user = member.getUser();
                        if (user != null && !user.getId().equals(currentUserID)) {
                            otherMember = member;
                            break;
                        }
                    }

                    // If we found another member, add their aa to the intent
                    if (otherMember != null) {
                        ListConversationResponse.Data.Member.User user = otherMember.getUser();
                        if (user != null) {
                            intent.putExtra("fullName", user.getFull_name());
                            intent.putExtra("avatar", user.getAvatar());
                            intent.putExtra("email", user.getEmail());
                            intent.putExtra("receiverId", user.getId());
                        }
                    }
                }

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return conversationList.size();
    }

    public static class ConversationViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar_other_user;
        TextView name_other_user;
        TextView lastMessages;

        public ConversationViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar_other_user = itemView.findViewById(R.id.avatar_other_user);
            name_other_user = itemView.findViewById(R.id.name_other_user);
            lastMessages = itemView.findViewById(R.id.lastMessages);

        }
    }

}
