package com.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.authservice.repository.*;
import com.authservice.dto.UserRegistrationDTO;
import com.authservice.model.*;


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