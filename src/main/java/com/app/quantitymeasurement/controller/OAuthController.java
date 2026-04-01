package com.app.quantitymeasurement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.quantitymeasurement.model.User;
import com.app.quantitymeasurement.repository.UserRepository;
import com.app.quantitymeasurement.security.JwtUtil;

import java.net.URI;

@RestController
public class OAuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/oauth/success")
    public ResponseEntity<?> googleLogin(@AuthenticationPrincipal OAuth2User principal) {

        if (principal == null) {
            // Redirect to login if user is not authenticated
            return ResponseEntity.status(302).location(URI.create("http://localhost:5173/login?error=oauth_failed")).build();
        }

        // Extracting attributes from Google
        String email = principal.getAttribute("email");
        String name = principal.getAttribute("name");

        if (email == null) {
            return ResponseEntity.badRequest().body("Email not shared by Google");
        }

        // Find or create user in database
        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setName(name != null ? name : email.split("@")[0]);
            newUser.setEmail(email);
            newUser.setRole("ROLE_USER");
            newUser.setProvider("GOOGLE");
            // For OAuth users, we usually leave the password null or set a random UUID
            return userRepository.save(newUser);
        });

        // Generate your custom JWT token
        String token = jwtUtil.generateToken(user);

        // Redirect to your frontend success page with the token in the URL
        String redirectUrl = "http://localhost:5173/oauth-success?token=" + token;
        
        return ResponseEntity
                .status(302)
                .location(URI.create(redirectUrl))
                .build();
    }
}