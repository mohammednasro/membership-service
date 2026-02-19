package com.appswave.membership.auth.security;

import java.io.IOException;

import org.slf4j.MDC;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {

			String path = request.getRequestURI();

			if (path.startsWith("/api/v1/auth/") || path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")) {
				filterChain.doFilter(request, response);
				return;
			}
			
			String token = getJwtFromRequest(request);

			if (token == null) {
				SecurityContextHolder.clearContext();
				throw new org.springframework.security.authentication.BadCredentialsException("Missing JWT token");
			}

			if (!jwtTokenProvider.validateToken(token)) {
				SecurityContextHolder.clearContext();
				throw new org.springframework.security.authentication.BadCredentialsException("Invalid JWT token");
			}

			String username = jwtTokenProvider.getUsernameFromToken(token);

			CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(username);

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
					null, userDetails.getAuthorities());

			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			MDC.put("userId", userDetails.getId());
			MDC.put("email", userDetails.getUsername());
			MDC.put("role",
					userDetails.getAuthorities().stream().findFirst().map(a -> a.getAuthority()).orElse("UNKNOWN"));

		} catch (Exception ex) {
			log.error("JWT authentication failed", ex);
			SecurityContextHolder.clearContext();
			throw new org.springframework.security.authentication.BadCredentialsException("Invalid JWT token", ex);
		}

		try {
			filterChain.doFilter(request, response);
		} finally {
			MDC.remove("userId");
			MDC.remove("email");
			MDC.remove("role");
		}

	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
