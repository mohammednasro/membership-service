package com.appswave.membership.auth.service;

import com.appswave.membership.auth.dto.JwtResponse;
import com.appswave.membership.auth.dto.LoginRequest;
import com.appswave.membership.auth.dto.RegisterRequest;

public interface AuthService {

    /**
     * Registers a new user with role USER by default.
     *
     * @param request DTO containing user registration data
     * @return JWT token response
     */
    JwtResponse register(RegisterRequest request);

    /**
     * Authenticates a user and returns a JWT token.
     *
     * @param request DTO containing login data
     * @return JWT token response
     */
    JwtResponse login(LoginRequest request);
}
