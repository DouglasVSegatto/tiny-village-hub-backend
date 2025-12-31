package com.segatto_builder.tinyvillagehub.controller;

import com.segatto_builder.tinyvillagehub.dto.user.AddressRequestDto;
import com.segatto_builder.tinyvillagehub.dto.user.UserResponseDto;
import com.segatto_builder.tinyvillagehub.mappers.UserMapper;
import com.segatto_builder.tinyvillagehub.model.User;
import com.segatto_builder.tinyvillagehub.security.IAuthFacade;
import com.segatto_builder.tinyvillagehub.service.IUserService;
import com.segatto_builder.tinyvillagehub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final IAuthFacade authFacade;
    private final UserMapper userMapper;
    private final IUserService userService;

    @GetMapping("/me")
    public UserResponseDto getCurrentUser() {
        User user = authFacade.getCurrentUser();
        return userMapper.toResponse(user);
    }

    @PutMapping("/address")
    public ResponseEntity<?> updateAddress(@RequestBody AddressRequestDto dto){
        userService.updateAddress(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
}