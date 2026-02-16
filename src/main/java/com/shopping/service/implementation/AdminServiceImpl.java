package com.shopping.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.shopping.dto.UserDetails;
import com.shopping.dto.UserResponseDTO;
import com.shopping.dto.UserStatusUpdateRequest;
import com.shopping.entity.User;
import com.shopping.exception.ResourceNotFoundException;
import com.shopping.repository.UserRepository;
import com.shopping.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public UserResponseDTO updateUserStatus(UserStatusUpdateRequest request) {

		User user = userRepo.findByUserId(request.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", request.getUserId()));

		user.setActive(request.isActive());

		userRepo.save(user);
		return new UserResponseDTO("User status updated successfully", user.getUserId(), HttpStatus.OK);
	}

	@Override
	public List<UserDetails> getAllUsers() {
		List<User> all = userRepo.findAll();
		List<UserDetails> allUsers = new ArrayList<>();
		UserDetails user = null;
		if (!all.isEmpty()) {
			for (User u : all) {
				if (!u.getRole().equalsIgnoreCase("admin")) {
					user = new UserDetails(u.getUserId(), u.getName(), u.getEmail(), u.isActive(), u.getCreatedAt());
					allUsers.add(user);
				}
			}
		}
		return allUsers;

	}

	@Override
	public Long getUserCount() {
		return userRepo.countByRoleIgnoreCase("User");
	}
}
