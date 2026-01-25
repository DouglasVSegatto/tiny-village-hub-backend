package com.segatto_builder.tinyvillagehub.service;

import com.segatto_builder.tinyvillagehub.client.ItemServiceClient;
import com.segatto_builder.tinyvillagehub.dto.user.AddressRequestDto;
import com.segatto_builder.tinyvillagehub.dto.user.UserRegistrationDto;
import com.segatto_builder.tinyvillagehub.mappers.AddressMapper;
import com.segatto_builder.tinyvillagehub.mappers.ItemMapper;
import com.segatto_builder.tinyvillagehub.model.Address;
import com.segatto_builder.tinyvillagehub.model.User;
import com.segatto_builder.tinyvillagehub.repository.UserRepository;
import com.segatto_builder.tinyvillagehub.security.IAuthFacade;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IAuthFacade authFacade;
    private final AddressMapper addressMapper;
    private final ItemServiceClient itemServiceClient;
    private final ItemMapper itemMapper;

    @Override
    public User registerNewUser(UserRegistrationDto registrationDto) throws IllegalStateException {

        if (userRepository.findByUsername(registrationDto.getUsername()).isPresent()) {
            throw new IllegalStateException("Username already taken.");
        }
        //TODO - Check if email already exists

        User newUser = new User();
        newUser.setUsername(registrationDto.getUsername());
        newUser.setEmail(registrationDto.getEmail());

        Address address = addressMapper.fromRegistration(registrationDto);
        newUser.setAddress(address);

        String encodedPassword = passwordEncoder.encode(registrationDto.getPassword());
        newUser.setPasswordHash(encodedPassword);

        log.info("REGISTERED user {}", newUser.getUsername());
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

    //TODO Future have a AddressSync in MicroService
    // OR
    // wait until upgrade method to use latitude/longitude coordinates
    @Override
    public void updateAddress(AddressRequestDto dto) {
        User user = authFacade.getCurrentUser();
        Address oldAddress = user.getAddress();
        Address newAddress = addressMapper.toModel(dto);

        try {
            user.setAddress(newAddress);
            userRepository.save(user);
            log.info("ADDRESS_UPDATED by user {}", user.getUsername());

            itemServiceClient.updateLocation(dto);
            log.info("ITEMS_ADDRESS_UPDATED by user {}", user.getUsername());

        } catch (Exception e) {
            log.error("Failed to sync items location for user {}, rolling back address", user.getUsername(), e);
            user.setAddress(oldAddress);
            userRepository.save(user);
            throw new RuntimeException("Address update failed - please try again", e);
        }
    }
}
