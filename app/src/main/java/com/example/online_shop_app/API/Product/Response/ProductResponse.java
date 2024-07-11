package com.example.online_shop_app.API.Product.Response;

import com.example.online_shop_app.API.Category.Response.CategoryResponse;
import com.example.online_shop_app.Models.Category;
import com.example.online_shop_app.Models.Product;

import java.util.List;

public class ProductResponse {
    private boolean success;
    private int status;
    private Data data;

    public ProductResponse(boolean success, int status, Data data) {
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

    public ProductResponse() {
    }

    public class Data {
        private List<Product> data;
        private int totalCount;
        private int pageCount;

        public Data() {
        }

        public Data(List<Product> data, int totalCount, int pageCount) {
            this.data = data;
            this.totalCount = totalCount;
            this.pageCount = pageCount;
        }

        public List<Product> getData() {
            return data;
        }

        public void setData(List<Product> data) {
            this.data = data;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }
    }
}
