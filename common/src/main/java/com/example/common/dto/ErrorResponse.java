package com.example.common.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ErrorResponse {
    private String requestId;
    private int errorCode;
    private String message;
    private List<String> details;
    private LocalDateTime timestamp;

    public static ErrorResponse of(String message) {
        return ErrorResponse.builder()
                .errorCode(400)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ErrorResponse of(int errorCode, String message) {
        return ErrorResponse.builder()
                .errorCode(errorCode)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ErrorResponse of(int errorCode, String message, List<String> details) {
        return ErrorResponse.builder()
                .errorCode(errorCode)
                .message(message)
                .details(details)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ErrorResponse of(String requestId, int errorCode, String message) {
        return ErrorResponse.builder()
                .requestId(requestId)
                .errorCode(errorCode)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ErrorResponse of(String requestId, int errorCode, String message, List<String> details) {
        return ErrorResponse.builder()
                .requestId(requestId)
                .errorCode(errorCode)
                .message(message)
                .details(details)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
