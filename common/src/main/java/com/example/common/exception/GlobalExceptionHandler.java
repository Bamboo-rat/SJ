package com.example.common.exception;

import com.example.common.dto.ErrorResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ValidateException.class)
    public ResponseEntity<ErrorResponse> handlingValidation(ValidateException exception) {
        return buildErrorResponse(exception.getErrorCode(), exception.getMessage(), List.of());
    }

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ErrorResponse> handlingBusiness(BusinessException exception) {
        return buildErrorResponse(exception.getErrorCode(), exception.getMessage(), List.of());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handlingUnexpected(Exception exception) {
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        return buildErrorResponse(errorCode, exception.getMessage(), List.of());
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(ErrorCode errorCode, String message, List<String> details) {
        ErrorResponse body = ErrorResponse.builder()
                .errorCode(errorCode.getCode())
                .message(message != null ? message : errorCode.getMessage())
                .details(details)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(errorCode.getStatus()).body(body);
    }
}
