package com.example.online_shop_app.API.Payment.Response;

import com.google.gson.annotations.SerializedName;

public class PaymentResponse {
    private boolean success;
    private int status;
    private Data data;

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

    public static class Data {
        @SerializedName("return_code")
        private int returnCode;

        @SerializedName("return_message")
        private String returnMessage;

        @SerializedName("sub_return_code")
        private int subReturnCode;

        @SerializedName("sub_return_message")
        private String subReturnMessage;

        @SerializedName("zp_trans_token")
        private String zpTransToken;

        @SerializedName("order_url")
        private String orderUrl;

        @SerializedName("order_token")
        private String orderToken;

        public int getReturnCode() {
            return returnCode;
        }

        public void setReturnCode(int returnCode) {
            this.returnCode = returnCode;
        }

        public String getReturnMessage() {
            return returnMessage;
        }

        public void setReturnMessage(String returnMessage) {
            this.returnMessage = returnMessage;
        }

        public int getSubReturnCode() {
            return subReturnCode;
        }

        public void setSubReturnCode(int subReturnCode) {
            this.subReturnCode = subReturnCode;
        }

        public String getSubReturnMessage() {
            return subReturnMessage;
        }

        public void setSubReturnMessage(String subReturnMessage) {
            this.subReturnMessage = subReturnMessage;
        }

        public String getZpTransToken() {
            return zpTransToken;
        }

        public void setZpTransToken(String zpTransToken) {
            this.zpTransToken = zpTransToken;
        }

        public String getOrderUrl() {
            return orderUrl;
        }

        public void setOrderUrl(String orderUrl) {
            this.orderUrl = orderUrl;
        }

        public String getOrderToken() {
            return orderToken;
        }

        public void setOrderToken(String orderToken) {
            this.orderToken = orderToken;
        }
    }
}
