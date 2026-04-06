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
}
