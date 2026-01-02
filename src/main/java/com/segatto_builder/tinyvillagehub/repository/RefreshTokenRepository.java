package com.segatto_builder.tinyvillagehub.repository;

import com.segatto_builder.tinyvillagehub.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByUserId(UUID userId);

    void deleteById(UUID id);

    @Modifying
    @Query("DELETE FROM refreshtoken rt WHERE rt.expiryDate < :now")
    void deleteExpiredTokens(Instant now);

    void deleteByToken(String token);
}