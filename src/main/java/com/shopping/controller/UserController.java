package com.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.dto.LoginDto;
import com.shopping.dto.UserRequestDTO;
import com.shopping.dto.UserResponseDTO;
import com.shopping.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public UserResponseDTO register(@RequestBody UserRequestDTO userDTO) {
		UserResponseDTO registerUser = userService.registerUser(userDTO);
		return registerUser;
	}

	@PostMapping("/login")
	public UserResponseDTO login(@RequestBody LoginDto userDTO) {
		UserResponseDTO loginUser = userService.loginUser(userDTO);
		return loginUser;
	}
}
