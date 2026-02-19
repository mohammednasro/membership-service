package com.appswave.membership.auth.exception;

import com.appswave.membership.auth.exception.enums.UserExceptionType;

public class UserException extends RuntimeException {
	
    private final UserExceptionType type;

    public UserException(UserExceptionType type, String message) {
        super(message);
        this.type = type;
    }

    public UserException(UserExceptionType type) { 
        super(type.getMessage());
        this.type = type;
    }

    public UserExceptionType getType() {
        return type;
    }
}
