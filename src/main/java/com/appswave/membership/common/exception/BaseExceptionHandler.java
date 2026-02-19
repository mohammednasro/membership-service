package com.appswave.membership.common.exception;

import org.slf4j.MDC;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseExceptionHandler {

    protected void logException(Exception ex, HttpServletRequest request) {
        String correlationId = MDC.get("correlationId");
        String userId = MDC.get("userId");
        String email = MDC.get("email");
        String role = MDC.get("role");

        log.error("Exception: path={}, userId={}, email={}, role={}, correlationId={}, message={}",
                request.getRequestURI(), userId, email, role, correlationId, ex.getMessage(), ex);
    }
}
