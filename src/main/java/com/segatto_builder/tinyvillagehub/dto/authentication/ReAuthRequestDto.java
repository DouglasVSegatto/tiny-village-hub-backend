package com.segatto_builder.tinyvillagehub.dto.authentication;

import lombok.Getter;

@Getter
public class ReAuthRequestDto {

    /**
     * Example JSON:
     {
     "password": "password123"
     }
     */

    private String password;
}
