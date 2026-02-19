package com.appswave.membership.common.exception;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.appswave.membership.auth.exception.UserException;
import com.appswave.membership.common.dto.ApiErrorResponse;
import com.appswave.membership.common.dto.ValidationErrorDTO;
import com.appswave.membership.common.i18n.MessageService;
import com.appswave.membership.member.exception.MemberException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends BaseExceptionHandler {

    private final MessageService messageService; // i18n messages


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleAll(Exception ex, HttpServletRequest request) {

        logException(ex, request);

        ApiErrorResponse response = ApiErrorResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .correlationId(MDC.get("correlationId"))
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex,
                                                             HttpServletRequest request) {

        logException(ex, request);

        List<ValidationErrorDTO> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::toValidationError)
                .collect(Collectors.toList());

        ApiErrorResponse response = ApiErrorResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(messageService.getMessage("validation.failed")) // رسالة عامة
                .path(request.getRequestURI())
                .correlationId(MDC.get("correlationId"))
                .errors(errors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private ValidationErrorDTO toValidationError(FieldError fieldError) {
        String localizedMessage = messageService.getMessage(fieldError.getDefaultMessage());
        // fieldError.getObjectName() ممكن تستخدم لو حبيت تشير للفورم كامل بدل الحقل
        return new ValidationErrorDTO(fieldError.getField(), localizedMessage);
    }


    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ApiErrorResponse> handleMemberException(MemberException ex,
                                                                  HttpServletRequest request) {

        logException(ex, request);

        ApiErrorResponse response = ApiErrorResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(messageService.getMessage("member.erorr."+ex.getType())) 
                .path(request.getRequestURI())
                .correlationId(MDC.get("correlationId"))
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiErrorResponse> handleMemberException(UserException ex,
                                                                  HttpServletRequest request) {

        logException(ex, request);

        ApiErrorResponse response = ApiErrorResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(messageService.getMessage("user.erorr."+ex.getType())) 
                .path(request.getRequestURI())
                .correlationId(MDC.get("correlationId"))
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        logException(ex, request);

        List<ValidationErrorDTO> errors = new ArrayList<>();

        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException ife) {

             String fieldName = ife.getPath().stream()
                    .map(ref -> ref.getPropertyName())
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse("unknown");

            String rejectedValue = String.valueOf(ife.getValue());

            String message = "Invalid value '" + rejectedValue + "' for field '" + fieldName + "'";
            messageService.getMessage("value.invalid", rejectedValue,fieldName);
            errors.add(ValidationErrorDTO.builder()
                    .path(fieldName)
                    .message(message)
                    .build());
        } else {
            errors.add(ValidationErrorDTO.builder()
                    .path("request")
                    .message(messageService.getMessage("malformed.json.request"))
                    .build());
        }

        ApiErrorResponse response = ApiErrorResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .errors(errors)
                .path(request.getRequestURI())
                .correlationId(MDC.get("correlationId"))
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
