package com.manager.api;

import com.manager.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/token")
    public ResponseEntity<String> generateToken(@RequestParam String phoneNumber) {
        String token = jwtUtil.generateToken(phoneNumber);
        return ResponseEntity.ok(token);
    }
}
