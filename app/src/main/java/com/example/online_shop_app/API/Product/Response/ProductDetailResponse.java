package com.example.online_shop_app.API.Product.Response;

import com.example.online_shop_app.Models.Product;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDetailResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("status")
    private int status;

    @SerializedName("data")
    private Product data;

    // Getters and setters

    public ProductDetailResponse(boolean success, int status, Product data) {
        this.success = success;
        this.status = status;
        this.data = data;
    }

    public ProductDetailResponse() {
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

    public Product getData() {
        return data;
    }

    public void setData(Product data) {
        this.data = data;
    }

    public static class Product {
        @SerializedName("id")
        private String id;

        @SerializedName("name")
        private String name;

        @SerializedName("description")
        private String description;

        @SerializedName("tags")
        private String tags;

        @SerializedName("originalPrice")
        private double originalPrice;

        @SerializedName("discountPrice")
        private double discountPrice;

        @SerializedName("quantity")
        private int quantity;

        @SerializedName("ratings")
        private int ratings;

        @SerializedName("status")
        private boolean status;

        @SerializedName("sold_out")
        private int soldOut;

        @SerializedName("createdAt")
        private String createdAt;

        @SerializedName("updatedAt")
        private String updatedAt;

        @SerializedName("brandId")
        private String brandId;
        private  Integer num_cart;

        @SerializedName("categoryId")
        private String categoryId;

        @SerializedName("brand")
        private Brand brand;

        @SerializedName("category")
        private Category category;

        @SerializedName("ProductImages")
        private List<Image> productImages;

        public Integer getNum_cart() {
            return num_cart;
        }

        public void setNum_cart(Integer num_cart) {
            this.num_cart = num_cart;
        }

        public Product(String id, String name,Category category , double originalPrice, double discountPrice, int quantity, Integer num_cart, List<Image> productImages) {
            this.id = id;
            this.name = name;
            this.category = category;
            this.originalPrice = originalPrice;
            this.discountPrice = discountPrice;
            this.quantity = quantity;
            this.num_cart = num_cart;
            this.productImages = productImages;
        }

        // Getters and setters
        // Inside Product class
        @Override
        public String toString() {
            return "Product{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", discountPrice=" + discountPrice +
                    ", originalPrice=" + originalPrice +
                    ", quantity=" + quantity +
                    ", addedQuantity=" + 1 + // Assuming addedQuantity is a new field you set
                    ", productImages=" + productImages +
                    '}';
        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public double getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(double originalPrice) {
            this.originalPrice = originalPrice;
        }

        public double getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(double discountPrice) {
            this.discountPrice = discountPrice;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getRatings() {
            return ratings;
        }

        public void setRatings(int ratings) {
            this.ratings = ratings;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public int getSoldOut() {
            return soldOut;
        }

        public void setSoldOut(int soldOut) {
            this.soldOut = soldOut;
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

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public Brand getBrand() {
            return brand;
        }

        public void setBrand(Brand brand) {
            this.brand = brand;
        }

        public Category getCategory() {
            return category;
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        public List<Image> getProductImages() {
            return productImages;
        }

        public void setProductImages(List<Image> productImages) {
            this.productImages = productImages;
        }

        public static class Brand {
            @SerializedName("id")
            private String id;

            @SerializedName("name")
            private String name;

            @SerializedName("image")
            private String image;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
            // Getters and setters
        }

        public static class Category {
            @SerializedName("id")
            private String id;

            @SerializedName("name")
            private String name;

            @SerializedName("image")
            private String image;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
            // Getters and setters
        }

        public static class Image {
            @SerializedName("id")
            private String id;

            @SerializedName("image_url")
            private String imageUrl;

            // Getters and setters

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

    }

}
