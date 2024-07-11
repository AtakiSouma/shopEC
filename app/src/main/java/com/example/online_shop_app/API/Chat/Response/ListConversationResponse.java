package com.example.online_shop_app.API.Chat.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListConversationResponse {
    private boolean success;
    private int status;
    @SerializedName("data")
    private List<Data> data;

    public ListConversationResponse(boolean success, int status, List<Data> data) {
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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        private String id;
        private String groupTitle;
        private String lastMessage;
        private String lastMessageId;
        private String createdAt;
        private String updatedAt;
        private List<Member> members;

        public Data(String id, String groupTitle, String lastMessage, String lastMessageId, String createdAt, String updatedAt, List<Member> members) {
            this.id = id;
            this.groupTitle = groupTitle;
            this.lastMessage = lastMessage;
            this.lastMessageId = lastMessageId;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.members = members;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGroupTitle() {
            return groupTitle;
        }

        public void setGroupTitle(String groupTitle) {
            this.groupTitle = groupTitle;
        }

        public String getLastMessage() {
            return lastMessage;
        }

        public void setLastMessage(String lastMessage) {
            this.lastMessage = lastMessage;
        }

        public String getLastMessageId() {
            return lastMessageId;
        }

        public void setLastMessageId(String lastMessageId) {
            this.lastMessageId = lastMessageId;
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

        public List<Member> getMembers() {
            return members;
        }

        public void setMembers(List<Member> members) {
            this.members = members;
        }

        public static class Member {
            private String id;
            private String userId;
            private String conversationId;
            private String createdAt;
            private User user;

            public Member(String id, String userId, String conversationId, String createdAt, User user) {
                this.id = id;
                this.userId = userId;
                this.conversationId = conversationId;
                this.createdAt = createdAt;
                this.user = user;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getConversationId() {
                return conversationId;
            }

            public void setConversationId(String conversationId) {
                this.conversationId = conversationId;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public User getUser() {
                return user;
            }

            public void setUser(User user) {
                this.user = user;
            }

            public static class User {
                private String avatar;
                private String email;
                private String full_name;
                private  String id;

                public User(String avatar, String email, String full_name, String id) {
                    this.avatar = avatar;
                    this.email = email;
                    this.full_name = full_name;
                    this.id = id;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
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
            }

        }


    }

}
