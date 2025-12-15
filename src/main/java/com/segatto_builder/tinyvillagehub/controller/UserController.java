package com.segatto_builder.tinyvillagehub.controller;

import com.segatto_builder.tinyvillagehub.dto.user.UserResponseDto;
import com.segatto_builder.tinyvillagehub.model.User;
import com.segatto_builder.tinyvillagehub.security.IAuthFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final IAuthFacade authFacade;

    @GetMapping("/me")
    public UserResponseDto getCurrentUser() {

        User user = authFacade.getCurrentUser();

        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}