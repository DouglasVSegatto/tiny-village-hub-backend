package com.segatto_builder.tinyvillagehub.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    /**
     * Example JSON:
     {
       "id": "550e8400-e29b-41d4-a716-446655440000",
       "username": "user1",
       "email": "user1@example.com",
       "joinDate": "2026-01-15T10:30:00",
       "neighborhood": "Downtown",
       "city": "Seattle",
       "state": "Washington",
       "country": "USA"
     }
     */

    private UUID id;
    private String username;
    private String email;
    private LocalDateTime joinDate;
    private String neighborhood;
    private String city;
    private String state;
    private String country;

}
