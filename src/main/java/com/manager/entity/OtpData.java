package com.manager.entity;

import java.time.LocalDateTime;

public class OtpData {
    private final String otp;
    private final LocalDateTime timestamp;

    public OtpData(String otp, LocalDateTime timestamp) {
        this.otp = otp;
        this.timestamp = timestamp;
    }

    public String getOtp() {
        return otp;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}