package com.authservice.dto;
import lombok.*;

@Data // This generates getEmail() and getPassword()
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    private String email;
    private String password;
}