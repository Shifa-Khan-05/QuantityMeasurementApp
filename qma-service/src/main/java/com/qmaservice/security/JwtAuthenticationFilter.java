package com.qmaservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // DEBUG 1: Is the token arriving?
        System.out.println("TRACE: Request to URL: " + request.getRequestURI());
        if (authHeader == null) {
            System.err.println("TRACE: Missing Authorization Header");
        } else if (!authHeader.startsWith("Bearer ")) {
            System.err.println("TRACE: Header format is NOT Bearer");
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String jwt = authHeader.substring(7);
                String userEmail = jwtUtil.extractUsername(jwt);

                // DEBUG 2: Did we extract an email?
                System.out.println("TRACE: Extracted Email: " + userEmail);

                if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    if (!jwtUtil.isTokenExpired(jwt)) {
                        
                        // DEBUG 3: Assigning Authority
                        System.out.println("TRACE: Granting ROLE_USER to " + userEmail);
                        
                        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userEmail, null, Collections.singletonList(authority)
                        );
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    } else {
                        System.err.println("TRACE: Token is Expired");
                    }
                }
            } catch (Exception e) {
                System.err.println("TRACE: Validation Error: " + e.getMessage());
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }
}