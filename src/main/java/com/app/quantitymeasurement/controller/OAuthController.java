package com.app.quantitymeasurement.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.quantitymeasurement.model.User;
import com.app.quantitymeasurement.repository.UserRepository;
import com.app.quantitymeasurement.security.JwtUtil;

@RestController
public class OAuthController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping("/oauth/success")
	public Map<String, String> googleLogin(@AuthenticationPrincipal OAuth2User principal) {
		String email = principal.getAttribute("email");
		String name = principal.getAttribute("name");

		// Use findByEmail and handle the "New User" logic clearly
		User user = userRepository.findByEmail(email).orElseGet(() -> {
			User newUser = new User();
			newUser.setName(name);
			newUser.setEmail(email);
			newUser.setRole("ROLE_USER");
			newUser.setProvider("GOOGLE"); // Identifying the source
			return userRepository.save(newUser);
		});

		String token = jwtUtil.generateToken(user);
		return Map.of("token", token);
	}
}