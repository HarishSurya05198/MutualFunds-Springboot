package com.mutual.funds.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mutual.funds.model.otpModel;
import com.mutual.funds.service.otpService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "*" , allowedHeaders = "*")
@RestController
@RequestMapping(value = "api/otp")
public class otpController {
	
	@Autowired
	private otpService otps;
	
	@PostMapping("send-otp")
	public Map<String, String> send_otp(@RequestBody otpModel input) {
		System.out.println("otp will be sent for email : "+input.getEmail());
		Boolean checkFlag = otps.sendOtp(input);
		Map<String,String> resp = new HashMap<>();
		if(checkFlag) {
			resp.put("message", "OTP Sent Successfully");
			return resp;
		}
		else {
			resp.put("message","Something went wrong");
			return resp;
		}
	}
	
	@PostMapping("verify-email")
	public Map<String,String> verify_otp(@RequestBody otpModel input) {
		System.out.println("otp entered : "+input.getOtp());
		Boolean checkFlag = otps.verifyOtp(input);
		Map<String,String> resp = new HashMap<>();
		if(checkFlag) {
			resp.put("message", "OTP Verified Successfully");
			return resp;
		}
		else {
			resp.put("message", "Invalid OTP");
			return resp;
		}
	}
	

}
