package com.example.online_shop_app.API.Payment.Response;

import com.google.gson.annotations.SerializedName;

public class OrderResponse {
    private boolean success;
    private int status;
    private Data data;

    public OrderResponse(boolean success, int status, Data data) {
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

    public static class Data {
        @SerializedName("payment_data")
        private PaymentData paymentData;
        private Order order;

        public Data(PaymentData paymentData, Order order) {
            this.paymentData = paymentData;
            this.order = order;
        }

        public PaymentData getPaymentData() {
            return paymentData;
        }

        public void setPaymentData(PaymentData paymentData) {
            this.paymentData = paymentData;
        }

        public Order getOrder() {
            return order;
        }

        public void setOrder(Order order) {
            this.order = order;
        }

        public static class PaymentData {
            private int returnCode;
            private String returnMessage;
            private int subReturnCode;
            private String subReturnMessage;
            private String zpTransToken;
            @SerializedName("order_url")
            private String orderUrl;
            private String orderToken;

            public PaymentData(int returnCode, String returnMessage, int subReturnCode, String subReturnMessage, String zpTransToken, String orderUrl, String orderToken) {
                this.returnCode = returnCode;
                this.returnMessage = returnMessage;
                this.subReturnCode = subReturnCode;
                this.subReturnMessage = subReturnMessage;
                this.zpTransToken = zpTransToken;
                this.orderUrl = orderUrl;
                this.orderToken = orderToken;
            }

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

        public static class Order {
            private String id;
            private double totalPrice;
            private String note;
            private String status;
            private String userId;
            private String createdAt;
            private String updatedAt;

            public Order(String id, double totalPrice, String note, String status, String userId, String createdAt, String updatedAt) {
                this.id = id;
                this.totalPrice = totalPrice;
                this.note = note;
                this.status = status;
                this.userId = userId;
                this.createdAt = createdAt;
                this.updatedAt = updatedAt;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public double getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(double totalPrice) {
                this.totalPrice = totalPrice;
            }

            public String getNote() {
                return note;
            }

            public void setNote(String note) {
                this.note = note;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
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

    }
}



