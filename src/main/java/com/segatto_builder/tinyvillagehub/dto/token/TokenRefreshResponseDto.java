package com.segatto_builder.tinyvillagehub.dto.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenRefreshResponseDto {

    /**
     * Example JSON:
     {
       "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
       "refreshToken": "550e8400-e29b-41d4-a716-446655440000"
     }
     */

    private String accessToken;
    private String refreshToken; // Return the same token or a new one
}