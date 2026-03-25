package com.app.quantitymeasurement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // 1. Disable CSRF for JWT/Stateless APIs
            .csrf(csrf -> csrf.disable())

            // 2. Configure Endpoint Permissions
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll() // Keep H2 access
                .requestMatchers("/auth/**").permitAll()       // Permit login/register
                .anyRequest().authenticated()                  // Protect all other APIs
            )

            // 3. Enable OAuth2 Login
            .oauth2Login(oauth->oauth.defaultSuccessUrl("/oauth/success",true))

            // 4. Handle H2 Frames (Needed to see the H2 UI in browser)
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))

            // 5. Session Management (Set to stateless if strictly using JWT)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            );

        return http.build();
    }

    // BCrypt Password Encoder for Local User Auth
   
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}