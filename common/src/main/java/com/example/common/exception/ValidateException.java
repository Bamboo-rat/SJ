package com.example.common.exception;

public class ValidateException extends BusinessException {
    private ErrorCode errorCode;

    public ValidateException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public ValidateException(ErrorCode errorCode, String message) {
        super(errorCode, message);
        this.errorCode = errorCode;
    }
}
