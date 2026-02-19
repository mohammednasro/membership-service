package com.appswave.membership.member.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberExceptionType {
    NOT_FOUND("MEMBER_NOT_FOUND");
    private final String code; // مجرد identifier للـ exception
}
