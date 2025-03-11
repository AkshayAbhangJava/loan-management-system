package com.manager.config;

import com.twilio.Twilio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TwilioInitializer {
	@Autowired
	private TwilioConfig twilioConfig;


    public TwilioInitializer(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
        System.out.println("Twilio Initialized Successfully!");
    }
}
