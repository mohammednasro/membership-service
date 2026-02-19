package com.appswave.membership.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ValidationErrorDTO {
    private String path;    
    private String message;
}
