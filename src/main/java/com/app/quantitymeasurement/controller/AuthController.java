package com.app.quantitymeasurement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.quantitymeasurement.dto.AuthRequest;
import com.app.quantitymeasurement.dto.AuthResponse;
import com.app.quantitymeasurement.dto.UserRegistrationDTO;
import com.app.quantitymeasurement.model.User;
import com.app.quantitymeasurement.security.JwtUtil;
import com.app.quantitymeasurement.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthService service;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/register")
	public User register(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
		return service.register(registrationDTO);
	}
	
	@PostMapping("/login")
	public AuthResponse login(@RequestBody AuthRequest request) {
		User user = service.login(request.getEmail(),request.getPassword());
		String token = jwtUtil.generateToken(user);
		
		return new AuthResponse(token);
		
	}
}