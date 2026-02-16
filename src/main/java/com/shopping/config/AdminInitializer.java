package com.shopping.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.shopping.entity.User;
import com.shopping.repository.UserRepository;

@Component
public class AdminInitializer implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {

		// Check if ADMIN already exists
		boolean adminExists = userRepository.findByEmail("admin@gmail.com").isPresent();

		if (!adminExists) {

			User admin = new User();
			admin.setName("Kunal Singh");
			admin.setEmail("admin@gmail.com");
			admin.setPassword(passwordEncoder.encode("admin123"));
			admin.setRole("ADMIN");
			admin.setActive(true);

			userRepository.save(admin);

			System.out.println("Default Admin Created ✅");
		}
	}
}
