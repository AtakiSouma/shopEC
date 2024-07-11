package com.example.online_shop_app.API.auth.Response;

public class LoginResponse {
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

    public class Data {
        private User user;
        private String access_token;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String getAccessToken() {
            return access_token;
        }

        public void setAccessToken(String access_token) {
            this.access_token = access_token;
        }

        public class User {
            private String email;
            private String full_name;
            private String id;
            private String role;
            private String role_name;

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getFullName() {
                return full_name;
            }

            public void setFullName(String full_name) {
                this.full_name = full_name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public String getRoleName() {
                return role_name;
            }

            public void setRoleName(String role_name) {
                this.role_name = role_name;
            }
        }
    }
}
