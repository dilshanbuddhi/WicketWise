package org.example.wicketwise.service;
import lombok.RequiredArgsConstructor;
import org.example.wicketwise.dto.AuthenticationRequest;
import org.example.wicketwise.dto.AuthenticationResponse;
import org.example.wicketwise.entity.Role;
import org.example.wicketwise.entity.User;
import org.example.wicketwise.exception.EmailAlreadyExistsException;
import org.example.wicketwise.exception.InvalidCredentialsException;
import org.example.wicketwise.exception.UserNotFoundException;
import org.example.wicketwise.repository.UserRepository;
import org.example.wicketwise.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    private static final String USER_NOT_FOUND_MSG = "User not found with email: %s";
    private static final String INVALID_CREDENTIALS_MSG = "Invalid email or password";
    private static final String EMAIL_EXISTS_MSG = "Email already in use: %s";

    public AuthenticationResponse register(AuthenticationRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(
                String.format(EMAIL_EXISTS_MSG, request.getEmail())
            );
        }
        
        try {
            // Create and save new user
            var user = User.builder()
                    .username(request.getEmail().split("@")[0]) // Use email prefix as username
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();
                    
            user = userRepository.save(user);
            
            // Generate JWT token
            var jwtToken = jwtService.generateToken(user);
            var expiration = jwtService.extractExpiration(jwtToken).toInstant();
            
            return AuthenticationResponse.success(
                    jwtToken,
                    user.getEmail(),
                    user.getRole(),
                    expiration
            );
        } catch (Exception e) {
            throw new RuntimeException("Error during user registration", e);
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            // Authenticate user
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
                )
            );
            
            // Get user details
            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UserNotFoundException(
                        String.format(USER_NOT_FOUND_MSG, request.getEmail())
                    ));
                    
            // Generate JWT token
            var jwtToken = jwtService.generateToken(user);
            var expiration = jwtService.extractExpiration(jwtToken).toInstant();
            
            return AuthenticationResponse.success(
                    jwtToken,
                    user.getEmail(),
                    user.getRole(),
                    expiration
            );
            
        } catch (BadCredentialsException ex) {
            throw new InvalidCredentialsException(INVALID_CREDENTIALS_MSG);
        } catch (AuthenticationException ex) {
            throw new InvalidCredentialsException("Authentication failed: " + ex.getMessage());
        }
    }
}
