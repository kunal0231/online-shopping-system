package com.shopping.service;

import com.shopping.dto.LoginDto;
import com.shopping.dto.UserRequestDTO;
import com.shopping.dto.UserResponseDTO;

public interface UserService {

	UserResponseDTO registerUser(UserRequestDTO userDTO);

	UserResponseDTO loginUser(LoginDto userDTO);

}
