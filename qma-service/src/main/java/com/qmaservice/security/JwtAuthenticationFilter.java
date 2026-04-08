package com.qmaservice.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String userId;

		// 1. Check if the header is missing or doesn't start with "Bearer "
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			// 2. Extract the token
			jwt = authHeader.substring(7);
			userId = jwtUtil.extractUsername(jwt);

			// 3. Authenticate if token is valid and context is empty
			if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				if (!jwtUtil.isTokenExpired(jwt)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId,
							null, new ArrayList<>());

					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}

			// Continue the filter chain
			filterChain.doFilter(request, response);

		} catch (ExpiredJwtException e) {
			// Handle Expired Token: Send clean 401 instead of crashing
			handleException(response, "JWT token has expired", HttpServletResponse.SC_UNAUTHORIZED);
		} catch (SignatureException e) {
			// Handle Invalid Signature
			handleException(response, "Invalid JWT signature", HttpServletResponse.SC_UNAUTHORIZED);
		} catch (Exception e) {
			// Handle any other JWT related errors
			e.printStackTrace();
			handleException(response, "Authentication failed: " + e.getMessage(), HttpServletResponse.SC_FORBIDDEN);
		}
	}

	/**
	 * Helper method to write a clean JSON error response
	 */
	private void handleException(HttpServletResponse response, String message, int status) throws IOException {
		response.setStatus(status);
		response.setContentType("application/json");
		// Write standard JSON structure that your frontend can read
		String jsonResponse = String.format("{\"message\": \"%s\", \"status\": %d}", message, status);
		response.getWriter().write(jsonResponse);
	}
}