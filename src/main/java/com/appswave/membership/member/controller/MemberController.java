package com.appswave.membership.member.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appswave.membership.common.util.ResponseHelper;
import com.appswave.membership.member.dto.MemberFilter;
import com.appswave.membership.member.dto.MemberRequest;
import com.appswave.membership.member.dto.MemberResponse;
import com.appswave.membership.member.facade.MemberFacade;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "Member Management", description = "APIs for managing members (CRUD, Search, Pagination, Filtering)")
public class MemberController {

	private final MemberFacade memberFacade;
	private final ResponseHelper responseHelper;

	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	@Operation(summary = "Create new member", description = "Creates a new member in the system. Requires ADMIN role.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Member created successfully", content = @Content(schema = @Schema(implementation = MemberResponse.class))),
			@ApiResponse(responseCode = "400", description = "Validation error"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "403", description = "Forbidden") })
	public ResponseEntity<com.appswave.membership.common.dto.ApiResponse<MemberResponse>> create(
			@Valid @RequestBody MemberRequest request) {
		log.info("Creating member: {}", request.getEmail());
		MemberResponse response = memberFacade.create(request);
		log.info("Member created with ID: {}", response.getId());

		return responseHelper.created("member.created.success", response);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@Operation(summary = "Get member by ID", description = "Fetch a member using its UUID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Member found", content = @Content(schema = @Schema(implementation = MemberResponse.class))),
			@ApiResponse(responseCode = "404", description = "Member not found") })

	public ResponseEntity<com.appswave.membership.common.dto.ApiResponse<MemberResponse>> getById(
			@PathVariable UUID id) {
		log.info("Fetching member by ID: {}", id);
		MemberResponse response = memberFacade.getById(id);
		return responseHelper.ok("member.fetched.success", response);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<com.appswave.membership.common.dto.ApiResponse<MemberResponse>> update(@PathVariable UUID id,
			@Valid @RequestBody MemberRequest request) {
		log.info("Updating member ID: {}", id);
		MemberResponse response = memberFacade.update(id, request);
		log.info("Member updated: {}", id);
		return responseHelper.ok("member.updated.success", response);
	}

	@PatchMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<com.appswave.membership.common.dto.ApiResponse<MemberResponse>> patch(@PathVariable UUID id,
			@RequestBody MemberRequest request) {
		log.info("Patching member ID: {}", id);
		MemberResponse response = memberFacade.patch(id, request);
		log.info("Member patched: {}", id);
		return responseHelper.ok("member.updated.success", response);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<com.appswave.membership.common.dto.ApiResponse<Void>> delete(@PathVariable UUID id) {
		log.info("Deleting member ID: {}", id);
		memberFacade.delete(id);
		log.info("Member deleted: {}", id);
		return responseHelper.ok("member.deleted.success", null);
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	@Operation(summary = "Search members", description = """
			Search members using optional filters.

			Supports:
			- Partial match on firstName, lastName, email, mobileNumber
			- Exact match on gender, membershipType, persona
			- Date range filtering (createdAt, updatedAt)
			- Pagination & Sorting

			Example:
			/api/v1/members?firstName=mo&gender=MALE&page=0&size=10&sortBy=createdAt&sortDirection=DESC
			""")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Members fetched successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MemberResponse.class))),
			@ApiResponse(responseCode = "400", description = "Invalid filter parameters"),
			@ApiResponse(responseCode = "403", description = "Forbidden") })

	public ResponseEntity<com.appswave.membership.common.dto.ApiResponse<Page<MemberResponse>>> search(
			@Valid MemberFilter filter) {
		log.info("Searching members with filter: {}", filter);
		Page<MemberResponse> page = memberFacade.search(filter);
		return responseHelper.ok("member.search.success", page);
	}
}
