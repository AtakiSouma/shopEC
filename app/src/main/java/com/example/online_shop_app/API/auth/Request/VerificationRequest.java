package com.example.online_shop_app.API.auth.Request;

public class VerificationRequest {
    private String email;

    public VerificationRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
