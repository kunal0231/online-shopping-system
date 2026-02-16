package com.shopping.dto;

import java.time.LocalDateTime;

public class UserDetails {
	private String userId;
	private String name;
	private String email;
	private boolean active;
	private LocalDateTime createdAt;

	public UserDetails(String userId, String name, String email, boolean active, LocalDateTime createdAt) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.active = active;
		this.createdAt = createdAt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}
