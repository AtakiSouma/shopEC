package com.example.online_shop_app.API.Chat.Response;

import com.example.online_shop_app.Models.Messages;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetListMessageResponse {
    private boolean success;
    private int status;
    @SerializedName("data")
    private List<Messages> data;

    public GetListMessageResponse(boolean success, int status, List<Messages> data) {
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

    public List<Messages> getData() {
        return data;
    }

    public void setData(List<Messages> data) {
        this.data = data;
    }



}
