package com.segatto_builder.tinyvillagehub.dto.authentication;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.UUID;

@Getter
public class LogoutRequestDto {

    @NotBlank(message = "Refresh Token is required")
    private String refreshToken;
}
