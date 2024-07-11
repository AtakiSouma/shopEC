package com.example.online_shop_app.API.auth.Response;

public class VerificationResponse {
    private boolean success;
    private int status;
    private Data data;

    public VerificationResponse(boolean success, int status, Data data) {
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private String message;
        private String verificationCode;

        public Data(String message, String verificationCode) {
            this.message = message;
            this.verificationCode = verificationCode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getVerificationCode() {
            return verificationCode;
        }

        public void setVerificationCode(String verificationCode) {
            this.verificationCode = verificationCode;
        }
    }
}
