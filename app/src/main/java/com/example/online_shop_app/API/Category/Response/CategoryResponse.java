package com.example.online_shop_app.API.Category.Response;

import com.example.online_shop_app.Models.Category;

import java.util.List;

public class CategoryResponse {
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
    // Getters and Setters

    public class Data {
        private List<Category> data;
        private int totalCount;
        private int pageCount;


        // Getters and Setters

        public List<Category> getData() {
            return data;
        }

        public void setData(List<Category> data) {
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
