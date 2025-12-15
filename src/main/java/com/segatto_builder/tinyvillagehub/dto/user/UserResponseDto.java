package com.segatto_builder.tinyvillagehub.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    /**
     * Example JSON:
     {
     "id": 1,
     "username": "user1",
     "email": "user1@example.com"
     }
     */

    private UUID id;
    private String username;
    private String email;

}
