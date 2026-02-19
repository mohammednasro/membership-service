package com.appswave.membership.member.dto;

import com.appswave.membership.common.validation.ValidEnum;
import com.appswave.membership.member.entity.Gender;
import com.appswave.membership.member.entity.MembershipType;
import com.appswave.membership.member.entity.Persona;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "MemberRequest", description = "Request payload for creating or updating a member")
public class MemberRequest {

    @Schema(description = "Member first name", example = "Mohammed")
    @NotBlank(message = "{member.firstname.required}")
    @Size(max = 50, message = "{member.firstname.size}")
    private String firstName;

    @Schema(description = "Member last name", example = "Nasro")
    @NotBlank(message = "{member.lastname.required}")
    @Size(max = 50, message = "{member.lastname.size}")
    private String lastName;

    @Schema(description = "Member email address", example = "mohammed.t.nasro@gmail.com")
    @NotBlank(message = "{member.email.required}")
    @Email(message = "{member.email.invalid}")
    private String email;

    @Schema(description = "Mobile number", example = "+962799999999")
    @Size(max = 20, message = "{member.mobile.size}")
    private String mobileNumber;

    @Schema(description = "Gender of the member", example = "MALE")
    @NotNull(message = "{member.gender.required}")
    @ValidEnum(enumClass = Gender.class, message = "{member.gender.invalid}")
    private Gender gender;

    @Schema(description = "Membership type", example = "INTERNAL_MEMBER")
    @NotNull(message = "{member.membershipType.required}")
    @ValidEnum(enumClass = MembershipType.class, message = "{member.membershipType.invalid}")
    private MembershipType membershipType;

    @Schema(description = "Member persona category", example = "BUSINESS")
    @NotNull(message = "{member.persona.required}")
    @ValidEnum(enumClass = Persona.class, message = "{member.persona.invalid}")
    private Persona persona;
}
