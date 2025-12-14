package com.segatto_builder.tinyvillagehub.controller;

import com.segatto_builder.tinyvillagehub.dto.authentication.LoginRequestDto;
import com.segatto_builder.tinyvillagehub.dto.authentication.LoginResponseDto;
import com.segatto_builder.tinyvillagehub.dto.token.TokenRefreshRequestDto;
import com.segatto_builder.tinyvillagehub.dto.token.TokenRefreshResponseDto;
import com.segatto_builder.tinyvillagehub.dto.user.UserRegistrationDto;
import com.segatto_builder.tinyvillagehub.model.RefreshToken;
import com.segatto_builder.tinyvillagehub.model.User;
import com.segatto_builder.tinyvillagehub.security.JwtService;
import com.segatto_builder.tinyvillagehub.service.IPrincipalDetailsService;
import com.segatto_builder.tinyvillagehub.service.PrincipalDetailsServiceImpl;
import com.segatto_builder.tinyvillagehub.service.RefreshTokenService;
import com.segatto_builder.tinyvillagehub.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.segatto_builder.tinyvillagehub.security.PrincipalDetails;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final IPrincipalDetailsService iPrincipalDetailsService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;


    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequestDto authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        // CAST to principaldetails
        final PrincipalDetails principalDetails = (PrincipalDetails) iPrincipalDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtService.generateToken(principalDetails);

        User user = principalDetails.getUser();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return ResponseEntity.ok(new LoginResponseDto(jwt, refreshToken.getToken(), user.getId(), user.getUsername()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDto registrationDto) {
        try {
            // Call the service layer to handle the business logic
            User registeredUser = userService.registerNewUser(registrationDto);

            // Return a success response, 201 Created
            return new ResponseEntity<>(
                    "User registered successfully. ID: " + registeredUser.getId(),
                    HttpStatus.CREATED
            );
        } catch (IllegalStateException e) {
            // Return an error response if username/email is taken, 400 Bad Request
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequestDto request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(token -> {
                    // Generate a new access token
                    String newAccessToken = jwtService.generateToken(token.getUser());

                    // Return the new tokens
                    return ResponseEntity.ok(new TokenRefreshResponseDto(newAccessToken, requestRefreshToken));
                })
                .orElseThrow(() -> new EntityNotFoundException("Refresh token not found in database."));
    }
}