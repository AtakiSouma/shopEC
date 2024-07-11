package com.example.online_shop_app.Models;

import java.util.List;

public class Order {
    private String id;
    private  String orderName;
    private int totalPrice;
    private String note;
    private String status;
    private String userId;
    private String createdAt;
    private String updatedAt;
    private User user;
    private Count _count;
    private List<OrderItem> OrderItem;

    public Order(String id,String orderName, int totalPrice, String note, String status, String userId, String createdAt, String updatedAt, User user, Count _count, List<Order.OrderItem> orderItem) {
        this.id = id;
        this.orderName = orderName;
        this.totalPrice = totalPrice;
        this.note = note;
        this.status = status;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this._count = _count;
        OrderItem = orderItem;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Count get_count() {
        return _count;
    }

    public void set_count(Count _count) {
        this._count = _count;
    }

    public List<Order.OrderItem> getOrderItem() {
        return OrderItem;
    }

    public void setOrderItem(List<Order.OrderItem> orderItem) {
        OrderItem = orderItem;
    }

    // Getters and setters
    public class OrderItem {
        private String id;
        private String orderId;
        private String productId;
        private int quantity;
        private int price;
        private Product product;

        // Getters and setters

        public OrderItem(String id, String orderId, String productId, int quantity, int price, Product product) {
            this.id = id;
            this.orderId = orderId;
            this.productId = productId;
            this.quantity = quantity;
            this.price = price;
            this.product = product;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }
    }

    public class Product {
        private String name;
        private List<ProductImage> ProductImages;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ProductImage> getProductImages() {
            return ProductImages;
        }

        public void setProductImages(List<ProductImage> productImages) {
            ProductImages = productImages;
        }

        // Getters and setters
        public class ProductImage {
            private String image_url;

            public String getImage_url() {
                return image_url;
            }

            public void setImage_url(String image_url) {
                this.image_url = image_url;
            }
            // Getters and setters
        }

    }

    public class User {
        private String id;
        private String email;
        private String full_name;
        private String address;
        private String phone_number;
        private  String fcmToken;

        public User(String id, String email, String full_name, String address, String phone_number, String fcmToken) {
            this.id = id;
            this.email = email;
            this.full_name = full_name;
            this.address = address;
            this.phone_number = phone_number;
            this.fcmToken = fcmToken;
        }

        public String getFcmToken() {
            return fcmToken;
        }

        public void setFcmToken(String fcmToken) {
            this.fcmToken = fcmToken;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFull_name() {
            return full_name;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }
        // Getters and setters
    }

    public class Count {
        private int OrderItem;

        // Getters and setters
    }
}
