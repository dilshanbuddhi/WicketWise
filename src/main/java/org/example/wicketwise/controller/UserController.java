package org.example.wicketwise.controller;

import jakarta.validation.Valid;
import org.example.wicketwise.dto.AuthenticationRequest;
import org.example.wicketwise.dto.AuthenticationResponse;
import org.example.wicketwise.dto.UserDto;
import org.example.wicketwise.entity.enums.Role;
import org.example.wicketwise.service.AuthenticationService;
import org.example.wicketwise.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@Valid @RequestBody AuthenticationRequest request) {
        // First create the user
        UserDto userDto = new UserDto();
        userDto.setUsername(request.getEmail().split("@")[0]);
        userDto.setEmail(request.getEmail());
        userDto.setPassword(request.getPassword());
        userDto.setRole(Role.SCORER);
        
        // Save the user
        UserDto createdUser = userService.createUser(userDto);
        
        // Authenticate and generate token
        AuthenticationResponse authResponse = authenticationService.register(request);
        
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }
    
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserDto> authenticate(
            @RequestParam String username,
            @RequestParam String password) {
        return ResponseEntity.ok(userService.authenticate(username, password));
    }
}
