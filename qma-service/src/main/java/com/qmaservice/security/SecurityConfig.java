package com.qmaservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	    http.csrf(csrf -> csrf.disable())
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .authorizeHttpRequests(authorize -> authorize

	            //VERY IMPORTANT (for Admin Server)
	            .requestMatchers("/actuator/**").permitAll()

	            // Public APIs
	            .requestMatchers(
	                "/api/v1/quantities/convert/**",
	                "/api/v1/quantities/compare"
	            ).permitAll()

	            // Protected APIs
	            .requestMatchers(
	                "/api/v1/quantities/add",
	                "/api/v1/quantities/subtract",
	                "/api/v1/quantities/divide",
	                "/api/v1/quantities/history"
	            ).authenticated()

	            .anyRequest().authenticated()
	        );

	    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}
}