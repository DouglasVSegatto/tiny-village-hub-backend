package com.segatto_builder.tinyvillagehub.controller;

import com.segatto_builder.tinyvillagehub.dto.authentication.LoginRequestDto;
import com.segatto_builder.tinyvillagehub.dto.authentication.LogoutRequestDto;
import com.segatto_builder.tinyvillagehub.dto.authentication.ReAuthRequestDto;
import com.segatto_builder.tinyvillagehub.dto.token.TokenRefreshRequestDto;
import com.segatto_builder.tinyvillagehub.dto.user.UserRegistrationDto;
import com.segatto_builder.tinyvillagehub.service.IAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequestDto dto) throws Exception {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDto registrationDto) {
        authService.register(registrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequestDto request) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.refreshToken(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutDevice(@RequestBody LogoutRequestDto dto) {
        authService.revokeToken(dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/logout-all-devices")
    public ResponseEntity<?> logoutAllDevices(@RequestBody ReAuthRequestDto dto) {
        authService.revokeAllTokens(dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}