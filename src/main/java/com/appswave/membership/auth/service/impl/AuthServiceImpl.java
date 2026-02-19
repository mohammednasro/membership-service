package com.appswave.membership.auth.service.impl;

import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.appswave.membership.auth.dto.JwtResponse;
import com.appswave.membership.auth.dto.LoginRequest;
import com.appswave.membership.auth.dto.RegisterRequest;
import com.appswave.membership.auth.entity.User;
import com.appswave.membership.auth.exception.UserException;
import com.appswave.membership.auth.exception.enums.UserExceptionType;
import com.appswave.membership.auth.repository.UserRepository;
import com.appswave.membership.auth.security.CustomUserDetails;
import com.appswave.membership.auth.security.JwtTokenProvider;
import com.appswave.membership.auth.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public JwtResponse register(RegisterRequest request) {
   
    	if(request==null)
    	{
    		throw new IllegalArgumentException("RegisterRequest is null!");
    	}
        if (Strings.isBlank(request.getEmail()) || Strings.isBlank(request.getPassword())|| request.getRole()==null) {
            throw new UserException(UserExceptionType.MISSING_REQUIRED_FIELD);
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserException(UserExceptionType.EMAIL_ALREADY_EXISTS);
        }
        
        
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(request.getRole())  
                .build();

        userRepository.save(user);
        String token = jwtTokenProvider.generateToken(new CustomUserDetails(user));

        return new JwtResponse(
                token,
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    @Override
    public JwtResponse login(LoginRequest request) { 

    	if(request==null)
    	{
    		throw new IllegalArgumentException("LoginRequest is null!");
    	}
        var authToken = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );
        authenticationManager.authenticate(authToken);

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtTokenProvider.generateToken(new CustomUserDetails(user));

        return new JwtResponse(
                token,
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }
}
