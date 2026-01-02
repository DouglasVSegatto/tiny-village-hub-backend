package com.segatto_builder.tinyvillagehub.util;

import com.segatto_builder.tinyvillagehub.service.IRefreshTokenService;
import com.segatto_builder.tinyvillagehub.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "scheduler.token-cleanup.enabled", havingValue = "true")
public class TokenCleanupScheduler {

    private final IRefreshTokenService refreshTokenService;

    //Daily
    @Scheduled(fixedRateString = "${scheduler.token-cleanup.interval}")
    public void cleanupExpiredTokens(){
        refreshTokenService.deleteExpiredTokens();
    }
}
