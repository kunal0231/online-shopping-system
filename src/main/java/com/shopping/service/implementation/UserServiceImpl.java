package com.shopping.service.implementation;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopping.dto.LoginDto;
import com.shopping.dto.UserRequestDTO;
import com.shopping.dto.UserResponseDTO;
import com.shopping.entity.User;
import com.shopping.exception.EmptyDtoException;
import com.shopping.repository.UserRepository;
import com.shopping.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public UserResponseDTO registerUser(UserRequestDTO userDTO) {
		if (userDTO == null) {
			throw new EmptyDtoException();
		}
		Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
		if (existingUser.isPresent()) {
			return new UserResponseDTO("Email already registered!", existingUser.get().getUserId(),
					HttpStatus.BAD_REQUEST);
		}

		Optional<User> lastUser = userRepository.findAll().stream().filter(u -> u.getUserId() != null).max((u1, u2) -> {
			int num1 = Integer.parseInt(u1.getUserId().replace("USER", ""));
			int num2 = Integer.parseInt(u2.getUserId().replace("USER", ""));
			return Integer.compare(num1, num2);
		});

		int nextNum = 1;

		if (lastUser.isPresent()) {
			int lastNum = Integer.parseInt(lastUser.get().getUserId().replace("USER", ""));
			nextNum = lastNum + 1;
		}

		String newProdId = String.format("USER%03d", nextNum);

		User user = new User(userDTO.getName(), userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword()),
				newProdId, "USER", true, LocalDateTime.now());

		userRepository.save(user);
		return new UserResponseDTO("User registered successfully!", user.getUserId(), HttpStatus.OK);
	}

	@Override
	public UserResponseDTO loginUser(LoginDto userDTO) {

		if (userDTO == null) {
			throw new EmptyDtoException();
		}

		Optional<User> optionalUser = userRepository.findByEmail(userDTO.getEmail());

		if (optionalUser.isEmpty()) {
			return new UserResponseDTO("User not found!", HttpStatus.BAD_REQUEST);
		}

		User user = optionalUser.get();

		try {

			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));

			return new UserResponseDTO("Login successful!", user.getUserId(), HttpStatus.OK);

		} catch (DisabledException e) {

			return new UserResponseDTO("Your account is inactive!", user.getUserId(), HttpStatus.FORBIDDEN);

		} catch (BadCredentialsException e) {

			return new UserResponseDTO("Invalid email or password!", HttpStatus.BAD_REQUEST);
		}
	}

}
