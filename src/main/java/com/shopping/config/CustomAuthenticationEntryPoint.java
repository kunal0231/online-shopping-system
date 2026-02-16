package com.shopping.config;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {

		response.setContentType("application/json");

		int status = HttpServletResponse.SC_UNAUTHORIZED;
		String message = "Authentication failed";

		if (authException instanceof DisabledException) {
			status = HttpServletResponse.SC_FORBIDDEN;
			message = "Your account has been blocked by Admin. Please contact support.";
		} else if (authException instanceof BadCredentialsException) {
			message = "Invalid email or password.";
		}

		response.setStatus(status);

		response.getWriter().write("""
				    {
				        "status": %d,
				        "message": "%s"
				    }
				""".formatted(status, message));
	}
}
