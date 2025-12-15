package com.segatto_builder.tinyvillagehub.service;

import com.segatto_builder.tinyvillagehub.dto.user.UserRegistrationDto;
import com.segatto_builder.tinyvillagehub.model.User;
import com.segatto_builder.tinyvillagehub.repository.UserRepository;
import com.segatto_builder.tinyvillagehub.security.IAuthFacade;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IAuthFacade authFacade;

    @Override
    public User registerNewUser(UserRegistrationDto registrationDto) throws IllegalStateException {

        if (userRepository.findByUsername(registrationDto.getUsername()).isPresent()) {
            throw new IllegalStateException("Username already taken.");
        }
        //TODO - Check if email already exists

        User newUser = new User();
        newUser.setUsername(registrationDto.getUsername());
        newUser.setEmail(registrationDto.getEmail());

        String encodedPassword = passwordEncoder.encode(registrationDto.getPassword());
        newUser.setPasswordHash(encodedPassword);

        return userRepository.save(newUser);
    }

    @Override
    public User findByUsername() {
        String username = authFacade.getCurrentUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    //Used by RefreshToken
    @Override
    public User findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
    }
}
