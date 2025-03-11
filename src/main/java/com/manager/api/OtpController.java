package com.manager.api;

import com.manager.service.OtpService;
import com.manager.dto.JwtResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendOtp(@RequestParam String phoneNumber) {
        return ResponseEntity.ok(otpService.sendOtp(phoneNumber));
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyOtp(@RequestParam String phoneNumber, @RequestParam String otp) {
        String token = otpService.verifyOtpAndGenerateToken(phoneNumber, otp);
        
        if ("Invalid OTP".equals(token) || "OTP expired".equals(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(token);
        }

        return ResponseEntity.ok(new JwtResponse(token)); // ✅ Return JWT Token
    }
}
