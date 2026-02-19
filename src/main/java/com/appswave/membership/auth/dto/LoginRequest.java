package com.appswave.membership.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "User login request")
public class LoginRequest {

	@Email
    @NotBlank
    @Schema(example = "user@example.com", description = "Registered email address")
    private String email;

    @Schema(example = "Password@123", description = "User password")
    @NotBlank
    private String password;
}
