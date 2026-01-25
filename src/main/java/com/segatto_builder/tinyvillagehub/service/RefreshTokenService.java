package com.segatto_builder.tinyvillagehub.service;

import com.segatto_builder.tinyvillagehub.model.RefreshToken;
import com.segatto_builder.tinyvillagehub.model.User;
import com.segatto_builder.tinyvillagehub.repository.RefreshTokenRepository;
import com.segatto_builder.tinyvillagehub.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService implements IRefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenDurationMs;

    /**
     * Creates and saves a new refresh token for a user.
     */
    @Override
    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());
        log.debug("TOKEN_CREATED by user {}", user.getUsername());
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
    @Transactional
    @Override
    public boolean verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            log.info("EXPIRED_TOKEN_REMOVED by user {}", token.getUser().getUsername());
            refreshTokenRepository.delete(token);
            return false;
        }
        return true;
    }

    @Transactional
    @Override
    public void deleteByToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Token not found"));
        log.debug("TOKEN_DELETED by user {}", refreshToken.getUser().getUsername());
        refreshTokenRepository.deleteByToken(token);
    }


    @Transactional
    @Override
    public void deleteByUserId(UUID userId, String username) {
        log.debug("ALL_TOKENS_DELETED by user {}", username);
        refreshTokenRepository.deleteByUserId(userId);
    }

    @Transactional
    @Override
    public void deleteExpiredTokens() {
        int deletedCount = refreshTokenRepository.deleteExpiredTokens(Instant.now());
        log.info("EXPIRED_TOKENS_CLEANUP removed {} tokens", deletedCount);
    }

    @Transactional
    @Override
    public RefreshToken rotateRefreshToken(String oldToken) {
        RefreshToken existingToken = findByToken(oldToken)
                .orElseThrow(() -> new EntityNotFoundException("Token not found"));

        existingToken.setToken(UUID.randomUUID().toString());
        existingToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));

        RefreshToken savedToken = refreshTokenRepository.save(existingToken);
        log.debug("TOKEN_ROTATED by user {}", existingToken.getUser().getUsername());
        return savedToken;
    }
}