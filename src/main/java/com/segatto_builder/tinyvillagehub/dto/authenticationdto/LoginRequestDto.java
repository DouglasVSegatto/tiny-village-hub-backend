package com.segatto_builder.tinyvillagehub.dto.authenticationdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    private String username;
    private String password;
}