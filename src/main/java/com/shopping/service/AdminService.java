package com.shopping.service;

import java.util.List;

import com.shopping.dto.UserDetails;
import com.shopping.dto.UserResponseDTO;
import com.shopping.dto.UserStatusUpdateRequest;

public interface AdminService {
	UserResponseDTO updateUserStatus(UserStatusUpdateRequest request);

	List<UserDetails> getAllUsers();

	Long getUserCount();
}
