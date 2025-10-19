package org.example.wicketwise.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.wicketwise.dto.UserDto;
import org.example.wicketwise.entity.User;
import org.example.wicketwise.entity.enums.Role;
import org.example.wicketwise.exception.BadRequestException;
import org.example.wicketwise.exception.ResourceNotFoundException;
import org.example.wicketwise.exception.UnauthorizedException;
import org.example.wicketwise.mapper.UserMapper;
import org.example.wicketwise.repository.UserRepo;
import org.example.wicketwise.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceIMPL implements UserService {

    private static final String USER_NOT_FOUND = "User not found with id: ";
    private static final String USERNAME_EXISTS = "Username is already taken";
    private static final String EMAIL_EXISTS = "Email is already in use";
    private static final String INVALID_CREDENTIALS = "Invalid username or password";
    private static final String UNAUTHORIZED_ACCESS = "You are not authorized to perform this action";

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        log.debug("Creating new user with username: {}", userDto.getUsername());
        
        validateUserDto(userDto);
        
        if (existsByUsername(userDto.getUsername())) {
            log.warn("Attempt to create user with existing username: {}", userDto.getUsername());
            throw new BadRequestException(USERNAME_EXISTS);
        }
        
        if (existsByEmail(userDto.getEmail())) {
            log.warn("Attempt to create user with existing email: {}", userDto.getEmail());
            throw new BadRequestException(EMAIL_EXISTS);
        }
        
        try {
            User user = userMapper.toEntity(userDto);
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            // Ensure new users have appropriate default role if not specified
            if (user.getRole() == null) {
                user.setRole(org.example.wicketwise.entity.Role.USER);
            }
            
            User savedUser = userRepo.save(user);
            log.info("Created new user with ID: {}", savedUser.getId());
            return userMapper.toDto(savedUser);
        } catch (Exception e) {
            log.error("Error creating user: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create user", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        log.debug("Fetching user with ID: {}", id);
        return userRepo.findById(id)
                .map(user -> {
                    checkAuthorization(user);
                    return userMapper.toDto(user);
                })
                .orElseThrow(() -> {
                    log.warn("User not found with ID: {}", id);
                    return new ResourceNotFoundException(USER_NOT_FOUND + id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByUsername(String username) {
        log.debug("Fetching user with username: {}", username);
        return userRepo.findByUsername(username)
                .map(user -> {
                    checkAuthorization(user);
                    return userMapper.toDto(user);
                })
                .orElseThrow(() -> {
                    log.warn("User not found with username: {}", username);
                    return new ResourceNotFoundException("User not found with username: " + username);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        log.debug("Fetching all users");
        checkAdminAccess();
        return userRepo.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        log.debug("Updating user with ID: {}", id);
        validateUserDto(userDto);
        
        return userRepo.findById(id)
                .map(existingUser -> {
                    checkAuthorization(existingUser);
                    
                    // Check if username is being changed and if it's already taken
                    if (!existingUser.getUsername().equals(userDto.getUsername()) && 
                        existsByUsername(userDto.getUsername())) {
                        log.warn("Attempt to change username to an existing one: {}", userDto.getUsername());
                        throw new BadRequestException(USERNAME_EXISTS);
                    }
                    
                    // Check if email is being changed and if it's already in use
                    if (!existingUser.getEmail().equals(userDto.getEmail()) && 
                        existsByEmail(userDto.getEmail())) {
                        log.warn("Attempt to change email to an existing one: {}", userDto.getEmail());
                        throw new BadRequestException(EMAIL_EXISTS);
                    }
                    
                    // Only allow role changes for admins
                    if (!hasRole(Role.ADMIN) && 
                        !existingUser.getRole().equals(userDto.getRole())) {
                        log.warn("Unauthorized role change attempt by non-admin user");
                        throw new UnauthorizedException("Only administrators can change user roles");
                    }
                    
                    userMapper.updateUserFromDto(userDto, existingUser);
                    
                    // Only update password if a new one is provided
                    if (StringUtils.hasText(userDto.getPassword())) {
                        existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
                    }
                    
                    User updatedUser = userRepo.save(existingUser);
                    log.info("Updated user with ID: {}", id);
                    return userMapper.toDto(updatedUser);
                })
                .orElseThrow(() -> {
                    log.warn("User not found for update with ID: {}", id);
                    return new ResourceNotFoundException(USER_NOT_FOUND + id);
                });
    }

    @Override
    public void deleteUser(Long id) {
        log.debug("Deleting user with ID: {}", id);
        
        User user = userRepo.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found for deletion with ID: {}", id);
                    return new ResourceNotFoundException(USER_NOT_FOUND + id);
                });
        
        checkAuthorization(user);
        
        try {
            userRepo.delete(user);
            log.info("Deleted user with ID: {}", id);
        } catch (Exception e) {
            log.error("Error deleting user with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to delete user with ID: " + id, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto authenticate(String username, String password) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            log.warn("Authentication attempt with empty username or password");
            throw new UnauthorizedException(INVALID_CREDENTIALS);
        }
        
        return userRepo.findByUsername(username)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(user -> {
                    log.info("User authenticated successfully: {}", username);
                    return userMapper.toDto(user);
                })
                .orElseThrow(() -> {
                    log.warn("Authentication failed for user: {}", username);
                    return new UnauthorizedException(INVALID_CREDENTIALS);
                });
    }
    
    // Helper methods
    private void validateUserDto(UserDto userDto) {
        if (userDto == null) {
            throw new BadRequestException("User data cannot be null");
        }
        
        if (!StringUtils.hasText(userDto.getUsername()) || 
            !StringUtils.hasText(userDto.getEmail()) ||
            !StringUtils.hasText(userDto.getPassword())) {
            throw new BadRequestException("Username, email, and password are required");
        }
        
        if (userDto.getUsername().length() < 4 || userDto.getUsername().length() > 50) {
            throw new BadRequestException("Username must be between 4 and 50 characters");
        }
        
        if (userDto.getPassword().length() < 8) {
            throw new BadRequestException("Password must be at least 8 characters long");
        }
    }
    
    private void checkAuthorization(User user) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        
        // Allow access if user is an admin or the account owner
        if (!hasRole(Role.ADMIN) && !user.getUsername().equals(currentUsername)) {
            log.warn("Unauthorized access attempt by user: {} to user data: {}", 
                    currentUsername, user.getUsername());
            throw new UnauthorizedException(UNAUTHORIZED_ACCESS);
        }
    }
    
    private boolean hasRole(Role role) {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_" + role.name()));
    }
    
    private void checkAdminAccess() {
        if (!hasRole(Role.ADMIN)) {
            log.warn("Non-admin user attempted to access admin-only endpoint");
            throw new UnauthorizedException(UNAUTHORIZED_ACCESS);
        }
    }
}
