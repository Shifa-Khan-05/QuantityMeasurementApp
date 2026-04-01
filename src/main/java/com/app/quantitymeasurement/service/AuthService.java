//package com.app.quantitymeasurement.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import com.app.quantitymeasurement.dto.UserRegistrationDTO;
//import com.app.quantitymeasurement.model.User;
//import com.app.quantitymeasurement.repository.UserRepository;
//
//import lombok.experimental.PackagePrivate;
//
//@Service
//public class AuthService {
//	@Autowired
//	private UserRepository repository;
//	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//	
//	// Register User (Hash Password)
//	public User register(UserRegistrationDTO userDto) {
//		User user = new User();
//		
//		
//		user.setName(userDto.getName());
//	    user.setEmail(userDto.getEmail());
//	    user.setMobile(userDto.getMobile());
//	    
//	    // Hash the password
//	    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
//	    
//	    // Hardcode the role so a hacker can't send "ROLE_ADMIN" in JSON
//	    user.setRole("ROLE_USER"); 
//	    user.setProvider("LOCAL");
//		
//		return repository.save(user);
//	}
//	
//	// Login User (Compare hashed password)
//	public User login(String email, String password) {
//		User user = repository.findByEmail(email).orElseThrow(()-> new RuntimeException("User Not Found"));
//		
//		if(!passwordEncoder.matches(password, user.getPassword())) {
//			throw new RuntimeException("Invalid Password");
//		}
//		return user;
//	}
//}















package com.app.quantitymeasurement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.quantitymeasurement.dto.UserRegistrationDTO;
import com.app.quantitymeasurement.model.User;
import com.app.quantitymeasurement.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ================= REGISTER =================
    public User register(UserRegistrationDTO dto) {

      System.out.println("REGISTER HIT");
    	System.out.println(dto);
        // ✅ FIX: use repository (not userRepository)
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setMobile(dto.getMobile());
        user.setRole("ROLE_USER");
        user.setProvider("LOCAL");

        return repository.save(user);
    }

    // ================= LOGIN =================
    public User login(String email, String password) {

        User user = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        // ✅ Correct password check
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }

        return user;
    }
}