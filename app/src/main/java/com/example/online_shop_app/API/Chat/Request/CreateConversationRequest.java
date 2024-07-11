package com.example.online_shop_app.API.Chat.Request;

import java.util.List;

public class CreateConversationRequest {
    private List<String> members;

    public CreateConversationRequest(List<String> members) {
        this.members = members;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}
