package com.segatto_builder.tinyvillagehub.security;

import com.segatto_builder.tinyvillagehub.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {
    String extractUsername(String token);
    String generateToken(UserDetails userDetails);
    String generateToken(User user);
    Boolean validateToken(String token, UserDetails userDetails);
}