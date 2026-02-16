package com.shopping.dto;

import org.springframework.http.HttpStatus;

public class UserResponseDTO {
	private String message;
	private HttpStatus status;
	private String userId;

	public UserResponseDTO() {
		super();
	}

	public UserResponseDTO(String message, HttpStatus status) {
		super();
		this.message = message;
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public UserResponseDTO(String success, String userId, HttpStatus status) {
		super();
		this.message = success;
		this.userId = userId;
		this.status = status;
	}

	public void setSuccess(HttpStatus status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
