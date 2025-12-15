package com.segatto_builder.tinyvillagehub.service;

import com.segatto_builder.tinyvillagehub.dto.user.UserRegistrationDto;
import com.segatto_builder.tinyvillagehub.model.User;

import java.util.UUID;

public interface IUserService {
    User registerNewUser(UserRegistrationDto registrationDto);
    User findByUsername();
    User findUserById(UUID userId);
}
