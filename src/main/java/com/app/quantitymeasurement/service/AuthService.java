package com.app.quantitymeasurement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.quantitymeasurement.dto.UserRegistrationDTO;
import com.app.quantitymeasurement.model.User;
import com.app.quantitymeasurement.repository.UserRepository;

import lombok.experimental.PackagePrivate;

@Service
public class AuthService {
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// Register User (Hash Password)
	public User register(UserRegistrationDTO userDto) {
		User user = new User();
		
		
		user.setName(userDto.getName());
	    user.setEmail(userDto.getEmail());
	    user.setMobile(userDto.getMobile());
	    
	    // Hash the password
	    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
	    
	    // Hardcode the role so a hacker can't send "ROLE_ADMIN" in JSON
	    user.setRole("ROLE_USER"); 
	    user.setProvider("LOCAL");
		
		return repository.save(user);
	}
	
	// Login User (Compare hashed password)
	public User login(String email, String password) {
		User user = repository.findByEmail(email).orElseThrow(()-> new RuntimeException("User Not Found"));
		
		if(!passwordEncoder.matches(password, user.getPassword())) {
			throw new RuntimeException("Invalid Password");
		}
		return user;
	}
}