package com.segatto_builder.tinyvillagehub.dto.token;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRefreshRequestDto {

    /**
     * Example JSON:
     {
       "refreshToken": "550e8400-e29b-41d4-a716-446655440000"
     }
     */

    @NotBlank
    private String refreshToken;
}