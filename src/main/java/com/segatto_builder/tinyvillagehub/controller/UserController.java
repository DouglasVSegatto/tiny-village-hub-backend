package com.segatto_builder.tinyvillagehub.controller;

import com.segatto_builder.tinyvillagehub.dto.user.UserRegistrationDto;
import com.segatto_builder.tinyvillagehub.dto.user.UserResponseDto;
import com.segatto_builder.tinyvillagehub.model.User;
import com.segatto_builder.tinyvillagehub.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserResponseDto getCurrentUser(@AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {

        // The principal object contains the details Spring Security loaded
        // via our CustomUserDetailsService (username, roles, etc.)

        // In a real application, you would load the full User entity
        // from the database using the username found in the principal.

        // For simplicity, let's map the core details available:

        // NOTE: Since the security principal only holds username/passwordHash,
        // you will need to add a findByUsername method to your UserService
        // to retrieve the full User entity (id, email).
        User foundUser = userService.findByUsername(principal.getUsername());

        return new UserResponseDto(
                foundUser.getId(),
                foundUser.getUsername(),
                foundUser.getEmail()
        );
    }
}