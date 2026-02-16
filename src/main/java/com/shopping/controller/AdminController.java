package com.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.dto.UserDetails;
import com.shopping.dto.UserResponseDTO;
import com.shopping.dto.UserStatusUpdateRequest;
import com.shopping.service.implementation.AdminServiceImpl;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminServiceImpl adminService;

	@GetMapping("/test")
	public String adminTest() {
		return "Admin Access Success";
	}

	@PutMapping("/user/status")
	public UserResponseDTO updateUserStatus(@RequestBody UserStatusUpdateRequest request) {

		return adminService.updateUserStatus(request);
	}

	@GetMapping("/get/all/users")
	public List<UserDetails> getAllUsers() {
		return adminService.getAllUsers();
	}

	@GetMapping("/users/count")
	public Long getAllUsersCount() {
		return adminService.getUserCount();
	}

}
