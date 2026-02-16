package com.shopping.dto;

public class UserStatusUpdateRequest {

    private String userId;
    private boolean active;

    public String getUserId() {
        return userId;
    }

    public boolean isActive() {
        return active;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
