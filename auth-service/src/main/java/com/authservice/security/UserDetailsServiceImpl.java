package com.authservice.security;

import com.authservice.model.*;

import com.authservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Find user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // FIX: Spring Security User constructor requires a non-null password.
        // If it's an OAuth user, the password field in DB might be null or empty.
        String password = user.getPassword();
        if (password == null || password.isEmpty()) {
            password = "OAUTH2_USER_NO_PASSWORD"; // Dummy value to satisfy the constructor
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), 
                password, 
                new ArrayList<>() // Authorities/Roles
        );
    }
}