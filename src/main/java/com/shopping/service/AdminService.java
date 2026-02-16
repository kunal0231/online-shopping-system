package com.shopping.service;

import com.shopping.dto.UserResponseDTO;
import com.shopping.dto.UserStatusUpdateRequest;

public interface AdminService {
	UserResponseDTO updateUserStatus(UserStatusUpdateRequest request);

}
