package com.example.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Auth
    UNAUTHENTICATED(1002, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1003, "You do not have permission", HttpStatus.FORBIDDEN),

    // Validation
    VALIDATION_ERROR(2001, "Validation error", HttpStatus.BAD_REQUEST),

    // System
    HANDLER_NOT_FOUND(4004, "Handler not found", HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR(5000, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;
    private final HttpStatus status;
}
