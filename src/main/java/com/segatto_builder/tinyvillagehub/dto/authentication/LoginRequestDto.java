package com.segatto_builder.tinyvillagehub.dto.authentication;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {

    /**
     * Example JSON:
     {
       "username": "user1",
       "password": "password123"
     }
     */

    private String username;
    private String password;
}