package com.manager;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.manager.config.TwilioConfig;
import com.twilio.Twilio;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableConfigurationProperties
public class GoldLoanManagementSystemApplication {

	@Autowired
	private TwilioConfig twilioConfigration;
	
	@PostConstruct
	public void setUp() {
		Twilio.init(twilioConfigration.getAccountSid() , twilioConfigration.getAuthToken());
	}
	
	public static void main(String[] args) {
		SpringApplication.run(GoldLoanManagementSystemApplication.class, args);
	}
	
	
}