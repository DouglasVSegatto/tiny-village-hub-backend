package com.segatto_builder.tinyvillagehub.service;

import com.segatto_builder.tinyvillagehub.dto.authentication.LoginRequestDto;
import com.segatto_builder.tinyvillagehub.dto.authentication.LoginResponseDto;
import com.segatto_builder.tinyvillagehub.dto.authentication.LogoutRequestDto;
import com.segatto_builder.tinyvillagehub.dto.authentication.ReAuthRequestDto;
import com.segatto_builder.tinyvillagehub.dto.token.TokenRefreshRequestDto;
import com.segatto_builder.tinyvillagehub.dto.token.TokenRefreshResponseDto;
import com.segatto_builder.tinyvillagehub.dto.user.UserRegistrationDto;
import com.segatto_builder.tinyvillagehub.model.RefreshToken;
import com.segatto_builder.tinyvillagehub.model.User;
import com.segatto_builder.tinyvillagehub.security.IAuthFacade;
import com.segatto_builder.tinyvillagehub.security.IJwtService;
import com.segatto_builder.tinyvillagehub.security.PrincipalDetails;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final IPrincipalDetailsService principalDetailsService;
    private final IJwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final IUserService userService;
    private final IRefreshTokenService refreshTokenService;
    private final IAuthFacade authFacade;

    @Override
    public LoginResponseDto login(LoginRequestDto dto) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getUsername(),
                            dto.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password", e);
        }

        // CAST to PrincipalDetails to access the custom 'getUser()' method, avoiding second db query.
        final PrincipalDetails principalDetails = (PrincipalDetails) principalDetailsService.loadUserByUsername(dto.getUsername());

        String jwt = jwtService.generateToken(principalDetails);

        User user = principalDetails.getUser();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return new LoginResponseDto(jwt, refreshToken.getToken(), user.getId(), user.getUsername());
    }

    @Override
    public void register(UserRegistrationDto dto) {
        userService.registerNewUser(dto);
    }

    @Override
    public TokenRefreshResponseDto refreshToken(TokenRefreshRequestDto dto) {
        String requestRefreshToken = dto.getRefreshToken();
        RefreshToken refreshToken = refreshTokenService.findByToken(requestRefreshToken)
                .orElseThrow(() -> new EntityNotFoundException("Refresh token not found."));
        // Validate token
        RefreshToken validToken = refreshTokenService.verifyExpiration(refreshToken);
        // Generate new access token
        String newAccessToken = jwtService.generateToken(validToken.getUser());

        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(validToken.getUser().getId());

        refreshTokenService.deleteByToken(requestRefreshToken);

        return new TokenRefreshResponseDto(newAccessToken, newRefreshToken.getToken());
    }

    @Override
    public void revokeToken(LogoutRequestDto dto) {
        UUID userId = authFacade.getCurrentUserId();
        RefreshToken refreshToken = refreshTokenService.findByToken(dto.getRefreshToken())
                .orElseThrow(() -> new EntityNotFoundException("Token not found."));

        if (!refreshToken.getUser().getId().equals(userId)) {
            throw new SecurityException("User is not authorized to update this Token.");
        }

        refreshTokenService.deleteByToken(dto.getRefreshToken());
    }

    @Override
    public void revokeAllTokens(ReAuthRequestDto dto) {

        User user = authFacade.getCurrentUser();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            dto.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password", e);
        }

        refreshTokenService.deleteByUserId(user.getId());

    }
}
