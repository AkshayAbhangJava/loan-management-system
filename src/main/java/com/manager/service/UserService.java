package com.manager.service;

import java.util.List;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.manager.entity.User;
import com.manager.repository.UserRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserService {
	
	@Autowired
    private UserRepository userRepository;

    @Value("${twilio.accountSid}")
    private String twilioAccountSid;
    
    @Value("${twilio.authToken}")
    private String twilioAuthToken;
    
    @Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;

//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//        Twilio.init(twilioAccountSid, twilioAuthToken);
//    }

//    @Transactional
//    public String sendOtp(String phoneNumber) {
//        String otp = generateOtp();
//        User user = userRepository.findByPhoneNumber(phoneNumber)
//                .orElse(new User());
//        user.setPhoneNumber(phoneNumber);
//        user.setOtp(otp);
//        user.setOtpGeneratedAt(LocalDateTime.now());
//        userRepository.save(user);
//
//        sendOtpViaTwilio(phoneNumber, otp);
//        return "OTP sent successfully";
//    }

//    public String verifyOtp(String phoneNumber, String otp) {
//        User user = userRepository.findByPhoneNumber(phoneNumber)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (user.getOtp().equals(otp) && 
//            user.getOtpGeneratedAt().isAfter(LocalDateTime.now().minusMinutes(5))) {
//            return "Login successful";
//        } else {
//            throw new RuntimeException("Invalid or expired OTP");
//        }
//    }
//
//    private String generateOtp() {
//        return String.valueOf(new Random().nextInt(900000) + 100000);
//    }

    private void sendOtpViaTwilio(String phoneNumber, String otp) {
        Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber(twilioPhoneNumber),
                "Your OTP code is: " + otp
        ).create();
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
