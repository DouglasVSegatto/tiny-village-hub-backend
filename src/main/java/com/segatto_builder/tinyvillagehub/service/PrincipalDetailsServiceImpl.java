package com.segatto_builder.tinyvillagehub.service;

import com.segatto_builder.tinyvillagehub.model.User;
import com.segatto_builder.tinyvillagehub.repository.UserRepository;
import com.segatto_builder.tinyvillagehub.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsServiceImpl implements IPrincipalDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new PrincipalDetails(user);
    }
}
