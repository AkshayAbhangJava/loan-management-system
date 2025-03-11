package com.manager.exception;

import java.time.LocalDateTime;

public class GoldLoanManagementSystemException extends Exception {
    private final ErrorInfo errorInfo;

    public GoldLoanManagementSystemException(String message, int errorCode) {
        super(message);
        this.errorInfo = new ErrorInfo(message, errorCode, LocalDateTime.now());
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }
}