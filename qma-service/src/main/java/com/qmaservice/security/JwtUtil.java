//package com.qmaservice.security;
//
//import java.nio.charset.StandardCharsets;
//
//import java.util.Date;
//import javax.crypto.SecretKey;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//
//@Component
//public class JwtUtil {
//
//	@Value("${jwt.secretkey}")
//	private String secretKey;
//
//	private SecretKey getSigningKey() {
//		return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
//	}
//
//	// Version 1: Accept User Object (Used in AuthController)
//	public String generateToken(String subject) {
//		return Jwts.builder().subject(subject).issuedAt(new Date())
//				.expiration(new Date(System.currentTimeMillis() + 10 * 60000)).signWith(getSigningKey()).compact();
//	}
//
//	public String extractUsername(String token) {
//		return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getSubject();
//	}
//
//	public boolean isTokenExpired(String token) {
//		return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getExpiration()
//				.before(new Date());
//	}
//}



package com.qmaservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    @Value("${jwt.secretkey}")
    private String secretKey;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
            "mysecretkeymysecretkeymysecretkey123456".getBytes(StandardCharsets.UTF_8)
        );
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()   // ✅ WILL WORK NOW
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}