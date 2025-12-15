package com.segatto_builder.tinyvillagehub.service;

import com.segatto_builder.tinyvillagehub.model.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface IRefreshTokenService {
    RefreshToken createRefreshToken(UUID userId);
    Optional<RefreshToken> findByToken(String token);
    RefreshToken verifyExpiration(RefreshToken token);
    int deleteByUserId(UUID userId);
}
