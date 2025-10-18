package org.example.wicketwise.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.wicketwise.entity.Role;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String email;
    private Role role;
    private Instant expiresAt;
    private String message;
    
    public static AuthenticationResponse success(String token, String email, Role role, Instant expiresAt) {
        return AuthenticationResponse.builder()
                .accessToken(token)
                .email(email)
                .role(role)
                .expiresAt(expiresAt)
                .message("Authentication successful")
                .build();
    }
}
