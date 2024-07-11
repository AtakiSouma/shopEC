package com.example.online_shop_app.API.Chat.Request;
// "senderId": "6b69954a-2f45-430d-b04b-fdbec67a1939",
//         "conversationId": "47862a76-9e8f-48ba-8678-3d7a334f1836",
//         "text": "Hi custome have you a good day"
public class CreateMessagesRequest {
    private String senderId;
    private  String conversationId;
    private  String text;

    public CreateMessagesRequest(String senderId, String conversationId, String text) {
        this.senderId = senderId;
        this.conversationId = conversationId;
        this.text = text;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
