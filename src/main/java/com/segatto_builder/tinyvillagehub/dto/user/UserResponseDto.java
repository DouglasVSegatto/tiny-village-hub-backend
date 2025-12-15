package com.segatto_builder.tinyvillagehub.dto.user;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
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
