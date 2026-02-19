package com.appswave.membership.member.dto;

import com.appswave.membership.member.entity.Gender;
import com.appswave.membership.member.entity.MembershipType;
import com.appswave.membership.member.entity.Persona;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@Schema(name = "MemberResponse", description = "Response object representing a member")
public class MemberResponse {

    @Schema(description = "Member unique identifier", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "First name", example = "Mohammed")
    private String firstName;

    @Schema(description = "Last name", example = "Nasro")
    private String lastName;

    @Schema(description = "Email address", example = "mohammed.nasro@example.com")
    private String email;

    @Schema(description = "Mobile number", example = "+962799999999")
    private String mobileNumber;

    @Schema(description = "Gender", example = "MALE")
    private Gender gender;

    @Schema(description = "Membership type", example = "PREMIUM")
    private MembershipType membershipType;

    @Schema(description = "Persona category", example = "TECH_ENTHUSIAST")
    private Persona persona;

    @Schema(description = "Creation timestamp", example = "2025-01-01T10:15:30Z")
    private Instant createdAt;

    @Schema(description = "Last update timestamp", example = "2025-01-10T12:30:00Z")
    private Instant updatedAt;
}
