package com.appswave.membership.auth.dto;

import com.appswave.membership.auth.entity.Role;
import com.appswave.membership.common.validation.ValidEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @Email(message = "{user.email.valid}")
    @NotBlank(message = "{user.email.notblank}")
    @Size(max = 100, message = "{user.email.size}")
    @Schema(example = "mohammed.t.nasro@gmail.com", description = "Registered email address")
    private String email;

    @NotBlank(message = "{user.password.notblank}")
    @Size(min = 8, max = 20, message = "{user.password.size}")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
        message = "{user.password.pattern}"
    )
    private String password;

    @NotBlank(message = "{user.firstname.notblank}")
    @Size(max = 50, message = "{user.firstname.size}")
    @Schema(description = "User first name", example = "Mohammed")
    private String firstName;

    @NotBlank(message = "{user.lastname.notblank}")
    @Size(max = 50, message = "{user.lastname.size}")
    @Schema(description = "User last name", example = "Nasro")
    private String lastName;

    
    
    @NotNull(message = "{user.role.notnull}")
    @Schema(description = "User Role (ADMIN or USER)", example = "ADMIN")
    @ValidEnum(enumClass = Role.class, message = "{user.role.invalid}")
    private Role role;
}
