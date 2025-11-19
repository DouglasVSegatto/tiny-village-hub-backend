package com.segatto_builder.tinyvillagehub.dto.authenticationdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDto {
    private final String jwt;
    private final String refreshToken;
    private final Long id;
    private final String username;
}