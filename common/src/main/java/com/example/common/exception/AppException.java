package com.example.common.exception;

public class AppException extends BusinessException {
    public AppException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AppException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
