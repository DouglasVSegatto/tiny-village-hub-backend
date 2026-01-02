package com.segatto_builder.tinyvillagehub.service;

import com.segatto_builder.tinyvillagehub.model.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface IRefreshTokenService {
    RefreshToken createRefreshToken(UUID userId);

    Optional<RefreshToken> findByToken(String token);

    boolean verifyExpiration(RefreshToken token);

    void deleteByToken(String token);

    void deleteByUserId(UUID userId);

    void deleteExpiredTokens();

    RefreshToken rotateRefreshToken(String oldToken);
}
