package com.segatto_builder.tinyvillagehub.service;

import com.segatto_builder.tinyvillagehub.dto.authentication.LoginRequestDto;
import com.segatto_builder.tinyvillagehub.dto.authentication.LoginResponseDto;
import com.segatto_builder.tinyvillagehub.dto.authentication.LogoutRequestDto;
import com.segatto_builder.tinyvillagehub.dto.authentication.ReAuthRequestDto;
import com.segatto_builder.tinyvillagehub.dto.token.TokenRefreshRequestDto;
import com.segatto_builder.tinyvillagehub.dto.token.TokenRefreshResponseDto;
import com.segatto_builder.tinyvillagehub.dto.user.UserRegistrationDto;

public interface IAuthService {
    LoginResponseDto login(LoginRequestDto dto);

    void register(UserRegistrationDto dto);

    TokenRefreshResponseDto refreshToken(TokenRefreshRequestDto dto);

    void revokeToken(LogoutRequestDto dto);

    void revokeAllTokens(ReAuthRequestDto dto);
}
