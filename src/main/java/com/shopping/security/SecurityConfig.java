package com.shopping.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.shopping.config.CustomAccessDeniedHandler;
import com.shopping.config.CustomAuthenticationEntryPoint;

@Configuration
public class SecurityConfig {

	private final CustomUserDetailsService customUserDetailsService;
	private final CustomAccessDeniedHandler accessDeniedHandler;
	private final CustomAuthenticationEntryPoint authenticationEntryPoint;

	public SecurityConfig(CustomUserDetailsService customUserDetailsService,
			CustomAccessDeniedHandler accessDeniedHandler, CustomAuthenticationEntryPoint authenticationEntryPoint) {
		this.customUserDetailsService = customUserDetailsService;
		this.accessDeniedHandler = accessDeniedHandler;
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	// ================= PASSWORD ENCODER =================
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// ================= AUTHENTICATION PROVIDER =================
	@Bean
	public AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}

	// ================= SECURITY FILTER CHAIN =================
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationProvider authenticationProvider)
			throws Exception {

		http.csrf(csrf -> csrf.disable())

				.authorizeHttpRequests(auth -> auth

						// Public endpoints
						.requestMatchers("/user/register", "/user/login", "/swagger-ui/**", "/v3/api-docs/**")
						.permitAll()

						// ================= PRODUCT SECURITY =================

						// ADMIN can create/update/delete
						.requestMatchers(HttpMethod.POST, "/products/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/products/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")

						// USER can view products
						.requestMatchers(HttpMethod.GET, "/products/**").hasRole("USER")

						// Existing role configs
						.requestMatchers("/admin/**").hasRole("ADMIN").requestMatchers("/user/**").hasRole("USER")

						.anyRequest().authenticated())

				.exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint)
						.accessDeniedHandler(accessDeniedHandler))

				.authenticationProvider(authenticationProvider)

				.httpBasic(Customizer.withDefaults());

		return http.build();
	}

	// ================= AUTHENTICATION MANAGER =================
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
