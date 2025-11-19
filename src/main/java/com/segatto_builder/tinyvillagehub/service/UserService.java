package com.segatto_builder.tinyvillagehub.service;

import com.segatto_builder.tinyvillagehub.dto.user.UserRegistrationDto;
import com.segatto_builder.tinyvillagehub.model.Item;
import com.segatto_builder.tinyvillagehub.model.User;
import com.segatto_builder.tinyvillagehub.repository.ItemRepository;
import com.segatto_builder.tinyvillagehub.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ItemRepository itemRepository;

    public User registerNewUser(UserRegistrationDto registrationDto) throws IllegalStateException {
        // 1. Basic Validation: Check if username already exists
        if (userRepository.findByUsername(registrationDto.getUsername()).isPresent()) {
            throw new IllegalStateException("Username already taken.");
        }

        // 2. Basic Validation: Check if email already exists
        // You might want to add a findByEmail method to UserRepository for this.

        // 3. Create the new User entity
        User newUser = new User();
        newUser.setUsername(registrationDto.getUsername());
        newUser.setEmail(registrationDto.getEmail());

        // 4. Securely hash the password before saving
        String encodedPassword = passwordEncoder.encode(registrationDto.getPassword());
        newUser.setPasswordHash(encodedPassword);

        // 5. Save the user to the database
        return userRepository.save(newUser);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public List<Item> findItemsByOwner(User owner) {
        return itemRepository.findByOwner(owner);
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
    }
}
