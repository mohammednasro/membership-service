package com.appswave.membership.common.util;

import com.appswave.membership.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResponseHelper {

    private final MessageSource messageSource;

    private String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }

    public <T> ResponseEntity<ApiResponse<T>> ok(String messageKey, T data, Object... args) {
        return ResponseEntity.ok(
                new ApiResponse<>(getMessage(messageKey, args), data)
        );
    }

    public <T> ResponseEntity<ApiResponse<T>> created(String messageKey, T data, Object... args) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(getMessage(messageKey, args), data));
    }

    public ResponseEntity<ApiResponse<Void>> noContent(String messageKey, Object... args) {
        return ResponseEntity.ok(
                new ApiResponse<>(getMessage(messageKey, args), null)
        );
    }
}
