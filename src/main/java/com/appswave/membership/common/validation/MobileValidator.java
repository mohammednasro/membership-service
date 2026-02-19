package com.appswave.membership.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MobileValidator implements ConstraintValidator<Mobile, String> {

    @Override
    public boolean isValid(String mobile, ConstraintValidatorContext context) {
        if (mobile == null || mobile.isEmpty()) {
            return true; 
        }
        return mobile.matches("^(\\+\\d{8,20}|0\\d{8,20})$");
    }
}
