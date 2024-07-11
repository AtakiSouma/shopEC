package com.example.online_shop_app.Models;

public class Messages {
    private String id;
    private String senderId;
    private String text;
    private String images;
    private String conversationId;
    private String createdAt;
    private String updatedAt;
    private  String receiverId;

    public Messages(String id, String senderId, String text, String images, String conversationId, String createdAt, String updatedAt, String receiverId) {
        this.id = id;
        this.senderId = senderId;
        this.text = text;
        this.images = images;
        this.conversationId = conversationId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.receiverId = receiverId;
    }

    public Messages() {
    }

    public Messages(String id, String senderId, String text, String images, String conversationId, String createdAt, String updatedAt) {
        this.id = id;
        this.senderId = senderId;
        this.text = text;
        this.images = images;
        this.conversationId = conversationId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Messages(String id, String senderId, String text, String images, String receiverId) {
        this.id = id;
        this.senderId = senderId;
        this.text = text;
        this.images = images;
        this.receiverId = receiverId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
