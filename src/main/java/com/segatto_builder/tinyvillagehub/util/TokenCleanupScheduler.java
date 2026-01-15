package com.segatto_builder.tinyvillagehub.util;

import com.segatto_builder.tinyvillagehub.service.IRefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "scheduler.token-cleanup.enabled", havingValue = "true")
public class TokenCleanupScheduler {

    private final IRefreshTokenService refreshTokenService;

    //Daily
    @Scheduled(fixedRateString = "${scheduler.token-cleanup.interval}")
    public void cleanupExpiredTokens() {
        try {
            log.info("Starting token cleanup job");
            refreshTokenService.deleteExpiredTokens();
            log.info("Token cleanup job completed successfully");
        } catch (Exception e) {
            log.error("Token cleanup job failed: {}", e.getMessage(), e);
        }
    }
}
