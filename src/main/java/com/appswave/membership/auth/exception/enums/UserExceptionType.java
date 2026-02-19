package com.appswave.membership.auth.exception.enums;

public enum UserExceptionType {

    USER_NOT_FOUND("User not found"),
    EMAIL_ALREADY_EXISTS("Email already exists"),
    MISSING_REQUIRED_FIELD("Required field is missing"),
    INVALID_CREDENTIALS("Invalid credentials");

    private final String message;

    UserExceptionType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
