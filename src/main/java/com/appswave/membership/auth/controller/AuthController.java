package com.appswave.membership.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appswave.membership.auth.dto.JwtResponse;
import com.appswave.membership.auth.dto.LoginRequest;
import com.appswave.membership.auth.dto.RegisterRequest;
import com.appswave.membership.auth.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication & Authorization APIs")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Register new user",
            description = "Creates a new user account and returns JWT token upon successful registration"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User registered successfully",
                    content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Validation error or email already exists"),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error")
    })
    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Registration request payload",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RegisterRequest.class))
            )
            @RequestBody RegisterRequest request) {

        JwtResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "User login",
            description = "Authenticates user credentials and returns JWT token"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Login successful",
                    content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Invalid credentials"),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized"),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error")
    })
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Login request payload",
                    required = true,
                    content = @Content(schema = @Schema(implementation = LoginRequest.class))
            )
            @RequestBody LoginRequest request) {

        JwtResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
