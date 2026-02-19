package com.appswave.membership.member.dto;

import java.time.Instant;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.appswave.membership.member.entity.Gender;
import com.appswave.membership.member.entity.MembershipType;
import com.appswave.membership.member.entity.Persona;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "MemberFilter", description = "Filtering and pagination parameters for searching members")
public class MemberFilter {

    @Schema(description = "Filter by first name (partial match)", example = "Moh")
    private String firstName;

    @Schema(description = "Filter by last name (partial match)", example = "Nas")
    private String lastName;

    @Schema(description = "Filter by email (partial match)", example = "example.com")
    private String email;

    @Schema(description = "Filter by mobile number", example = "+96279")
    private String mobileNumber;

    @Schema(description = "Filter by gender", example = "MALE")
    private Gender gender;

    @Schema(description = "Filter by membership type", example = "PREMIUM")
    private MembershipType membershipType;

    @Schema(description = "Filter by persona", example = "TECH_ENTHUSIAST")
    private Persona persona;

    @Schema(description = "Page number (0-based)", example = "0", defaultValue = "0")
    private int page = 0;

    @Schema(description = "Page size", example = "20", defaultValue = "20")
    private int size = 20;

    @Schema(description = "Field to sort by", example = "createdAt", defaultValue = "createdAt")
    private String sortBy = "createdAt";

    @Schema(description = "Sort direction (ASC or DESC)", example = "DESC", defaultValue = "DESC")
    private String sortDirection = "DESC";

    public Pageable toPageable() {
        Sort sort = Sort.by(sortBy);
        sort = "DESC".equalsIgnoreCase(sortDirection) ? sort.descending() : sort.ascending();
        return PageRequest.of(page, size, sort);
    }
}
