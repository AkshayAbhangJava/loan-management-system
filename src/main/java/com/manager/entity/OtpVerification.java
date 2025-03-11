package com.manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "OTP_VERIFICATIONS")
public class OtpVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "otp", nullable = false)
    private String otp;

    @Column(name = "otp_generated_at", nullable = false)
    private Long otpGeneratedAt;

    public OtpVerification() {}

    public OtpVerification(String phoneNumber, String otp, Long otpGeneratedAt) {
        this.phoneNumber = phoneNumber;
        this.otp = otp;
        this.otpGeneratedAt = otpGeneratedAt;
    }
}
