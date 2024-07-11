package com.example.online_shop_app.API.Chat.Response;

import com.example.online_shop_app.Models.Messages;
import com.google.gson.annotations.SerializedName;

public class CreateMessageResponse {
    private boolean success;
    private int status;
    @SerializedName("data")
    private Messages data;

    public CreateMessageResponse(boolean success, int status, Messages data) {
        this.success = success;
        this.status = status;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Messages getData() {
        return data;
    }

    public void setData(Messages data) {
        this.data = data;
    }
}
