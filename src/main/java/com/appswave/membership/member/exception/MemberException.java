package com.appswave.membership.member.exception;

import com.appswave.membership.member.exception.enums.MemberExceptionType;

import lombok.Getter;

@Getter
public class MemberException extends RuntimeException {

    private final MemberExceptionType type;
    private final Object[] args; 

    public MemberException(MemberExceptionType type, Object... args) {
        super(type.getCode()); 
        this.type = type;
        this.args = args;
    }
}
