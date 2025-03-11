package com.manager.service;

import com.manager.config.TwilioConfig;
import com.manager.entity.User;
import com.manager.entity.OtpVerification;
import com.manager.repository.UserRepository;
import com.manager.repository.OtpVerificationRepository;
import com.manager.security.JwtUtil;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {

    private final TwilioConfig twilioConfig;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final OtpVerificationRepository otpRepository;
    private static final int OTP_EXPIRATION_MINUTES = 5;

    public OtpService(TwilioConfig twilioConfig, JwtUtil jwtUtil, 
                      UserRepository userRepository, OtpVerificationRepository otpRepository) {
        this.twilioConfig = twilioConfig;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.otpRepository = otpRepository;
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }

    @Transactional
    public String sendOtp(String phoneNumber) {
        String formattedPhone = formatPhoneNumber(phoneNumber);
        String otp = generateOtp();

        // Save OTP to OTP_VERIFICATIONS table
        Optional<OtpVerification> existingOtp = otpRepository.findByPhoneNumber(formattedPhone);
        if (existingOtp.isPresent()) {
            existingOtp.get().setOtp(otp);
            existingOtp.get().setOtpGeneratedAt(System.currentTimeMillis());
            otpRepository.save(existingOtp.get());
        } else {
            OtpVerification newOtp = new OtpVerification(formattedPhone, otp, System.currentTimeMillis());
            otpRepository.save(newOtp);
        }

        // Send OTP via Twilio
        Message.creator(
                new PhoneNumber(formattedPhone),
                new PhoneNumber(twilioConfig.getPhoneNumber()),
                "Your OTP code is: " + otp + ". It is valid for " + OTP_EXPIRATION_MINUTES + " minutes."
        ).create();

        return "OTP sent successfully to " + formattedPhone;
    }

    @Transactional
    public String verifyOtpAndGenerateToken(String phoneNumber, String otp) {
        String formattedPhone = formatPhoneNumber(phoneNumber);
        Optional<OtpVerification> otpEntry = otpRepository.findByPhoneNumber(formattedPhone);

        if (otpEntry.isEmpty() || isOtpExpired(otpEntry.get().getOtpGeneratedAt())) {
            return "Invalid or expired OTP";
        }

        if (!otpEntry.get().getOtp().equals(otp)) {
            return "Invalid OTP";
        }

        // Delete OTP after successful verification
        otpRepository.delete(otpEntry.get());

        // Check if user exists, if not, save in USERS table
        if (!userRepository.existsByPhoneNumber(formattedPhone)) {
            User newUser = new User();
            newUser.setPhoneNumber(formattedPhone);
            userRepository.save(newUser);
        }

        return jwtUtil.generateToken(formattedPhone);
    }

    private boolean isOtpExpired(long timestamp) {
        return System.currentTimeMillis() - timestamp > TimeUnit.MINUTES.toMillis(OTP_EXPIRATION_MINUTES);
    }

    private String formatPhoneNumber(String phoneNumber) {
        if (!phoneNumber.startsWith("+")) {
            phoneNumber = "+91" + phoneNumber; // Assuming Indian numbers
        }
        return phoneNumber;
    }

    private String generateOtp() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }
}
