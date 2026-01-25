package com.segatto_builder.tinyvillagehub.service;

import com.segatto_builder.tinyvillagehub.model.RefreshToken;
import com.segatto_builder.tinyvillagehub.model.User;

import java.util.Optional;
import java.util.UUID;

public interface IRefreshTokenService {
    RefreshToken createRefreshToken(User user);

    Optional<RefreshToken> findByToken(String token);

    boolean verifyExpiration(RefreshToken token);

    void deleteByToken(String token);

    void deleteByUserId(UUID userId, String username);

    void deleteExpiredTokens();

    RefreshToken rotateRefreshToken(String oldToken);
}
