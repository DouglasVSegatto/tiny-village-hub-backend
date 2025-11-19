package com.segatto_builder.tinyvillagehub.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;

}
