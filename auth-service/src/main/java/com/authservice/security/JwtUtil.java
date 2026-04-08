package com.authservice.security;

import java.nio.charset.StandardCharsets;

import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.authservice.model.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.secretkey}")
	private String secretKey;

//	private SecretKey getSigningKey() {
//		return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
//	}
	
	private SecretKey getSigningKey() {
	    return Keys.hmacShaKeyFor(
	        "mysecretkeymysecretkeymysecretkey123456".getBytes(StandardCharsets.UTF_8)
	    );
	}

	// Version 1: Accept User Object (Used in AuthController)
	public String generateToken(User user) {
		return Jwts.builder().subject(user.getEmail()).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 10 * 60000)).signWith(getSigningKey()).compact();
	}

	// Version 2: Accept String Email (Used in SecurityConfig/OAuth Flow)
	public String generateToken(String email) {
		return Jwts.builder().subject(email).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 10 * 60000)).signWith(getSigningKey()).compact();
	}

	public String extractUsername(String token) {
		return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getSubject();
	}

	public boolean isTokenExpired(String token) {
		return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getExpiration()
				.before(new Date());
	}
}