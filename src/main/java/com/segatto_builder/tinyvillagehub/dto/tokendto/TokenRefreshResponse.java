package com.segatto_builder.tinyvillagehub.dto.tokendto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenRefreshResponse {
    private String accessToken;
    private String refreshToken; // Return the same token or a new one
}