package com.segatto_builder.tinyvillagehub.security;

import com.segatto_builder.tinyvillagehub.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthFacade implements IAuthFacade {

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public User getCurrentUser() {

        Authentication authentication = getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof String && "anonymousUser".equals(principal)) {
            return null;
        }

        if (principal instanceof PrincipalDetails principalDetails) {
            return principalDetails.getUser();
        }

        // Safeguard for other scenarios
        return null;
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public String getCurrentUsername() {
        return getCurrentUser().getUsername();
    }
}
