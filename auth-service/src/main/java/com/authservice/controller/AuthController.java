package com.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authservice.dto.*;
import com.authservice.dto.UserRegistrationDTO;
import com.authservice.model.User;
import com.authservice.repository.*;
import com.authservice.security.JwtUtil;
import com.authservice.service.*;



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
		String token = jwtUtil.generateToken(user.getEmail());
		
		return new AuthResponse(token);
		
	}
}