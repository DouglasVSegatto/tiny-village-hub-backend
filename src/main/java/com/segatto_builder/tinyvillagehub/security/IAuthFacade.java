package com.segatto_builder.tinyvillagehub.security;

import com.segatto_builder.tinyvillagehub.model.User;
import org.springframework.security.core.Authentication;

public interface IAuthFacade {

    //Spring Security Authentication object for the current request.
    Authentication getAuthentication();

    //Core method used by service layers.
    User getCurrentUser();
}