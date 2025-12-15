package com.segatto_builder.tinyvillagehub.service;

import com.segatto_builder.tinyvillagehub.model.RefreshToken;
import com.segatto_builder.tinyvillagehub.model.User;
import com.segatto_builder.tinyvillagehub.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService implements IRefreshTokenService{

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final IUserService userService;

    /**
     * Creates and saves a new refresh token for a user.
     */
    @Override
    public RefreshToken createRefreshToken(UUID userId) {
        User user = userService.findUserById(userId); // Assuming you have a findUserById method

        // Check if a token already exists for the user and delete it (optional cleanup)
        // refreshTokenRepository.deleteByUser(user);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString()); // Use a long random string

        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * Finds and verifies if the token is valid (not expired).
     */
    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * Verifies expiration. Throws exception if token is expired.
     */
    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Transactional
    @Override
    public int deleteByUserId(UUID userId) {
        User user = userService.findUserById(userId);
        return refreshTokenRepository.deleteByUser(user);
    }
}