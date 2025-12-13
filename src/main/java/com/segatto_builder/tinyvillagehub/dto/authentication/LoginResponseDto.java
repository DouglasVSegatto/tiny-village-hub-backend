package com.segatto_builder.tinyvillagehub.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDto {

    /**
     * Example JSON:
     {
       "jwt": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
       "refreshToken": "550e8400-e29b-41d4-a716-446655440000",
       "id": 1,
       "username": "user1"
     }
     */

    private final String jwt;
    private final String refreshToken;
    private final Long id;
    private final String username;
}