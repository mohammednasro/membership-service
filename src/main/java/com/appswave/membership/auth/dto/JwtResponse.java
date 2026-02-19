package com.appswave.membership.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "JWT authentication response")
public class JwtResponse {

    @Schema(description = "JWT access token")
    private String token;

    @Schema(example = "Bearer", description = "Token type")
    private String type = "Bearer";

    @Schema(example = "user@example.com")
    private String email;

    @Schema(example = "Mohammed")
    private String firstName;

    @Schema(example = "Nasro")
    private String lastName;

    public JwtResponse(String token, String email, String firstName, String lastName) {
        this.token = token;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}


