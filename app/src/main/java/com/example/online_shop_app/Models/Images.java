package com.example.online_shop_app.Models;

import com.google.gson.annotations.SerializedName;

public class Images {
    @SerializedName("id")
    private String id;

    @SerializedName("image_url")
    private String imageUrl;

    public Images() {
    }

    public Images(String id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
