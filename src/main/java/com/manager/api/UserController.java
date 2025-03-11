package com.manager.api;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.manager.entity.User;
import com.manager.service.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
    private UserService userService;

    //todo-> remove constructor instead use class level @noarugconstruor @allarugconstructor!!!!!!!!!!!!!!!
    
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }

//    @PostMapping("/send-otp")
//    public ResponseEntity<String> sendOtp(@RequestBody UserDTO userDTO) {
//        String message = userService.sendOtp(userDTO.getPhoneNumber());
//        return ResponseEntity.ok(message);
//    }

//    @PostMapping("/verify-otp")
//    public ResponseEntity<String> verifyOtp(@RequestBody OtpDTO otpDTO) {
//        String message = userService.verifyOtp(otpDTO.getPhoneNumber(), otpDTO.getOtp());
//        return ResponseEntity.ok(message);
//    }
    
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
