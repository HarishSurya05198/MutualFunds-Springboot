package com.mutual.funds.service;

import java.text.DecimalFormat;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.mutual.funds.entity.otpEntity;
import com.mutual.funds.model.otpModel;
import com.mutual.funds.repository.otpRepo;


@Service
public class otpService {
	
	@Value("${spring.mail.username}")
	private String fromMail;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private otpRepo otpsave;
	
	public Boolean sendOtp(otpModel u) {
		try {
			String otp = generateOTP();
			String toMail = u.getEmail();
			String motp = "Dear user , OTP: " + otp
					+ " is for verification of your account for Login to Mutual Funds Application.";
			String key = keygen(u.getEmail());
			otpEntity otpval = null;
			Optional<otpEntity> temp_val = otpsave.findByKey(key);
			if (temp_val.isPresent()) {
				otpval = temp_val.get();
				otpval.setOtpval(otp);
			} else {
				otpval = new otpEntity();
				otpval.setKey(key);
				otpval.setOtpval(otp);
			}
			otpsave.save(otpval);
			SimpleMailMessage otpMail = new SimpleMailMessage();
			otpMail.setFrom(fromMail);
			otpMail.setTo(u.getEmail());
			otpMail.setSubject("OTP for Login");
			otpMail.setText(motp);
			mailSender.send(otpMail);
			return true;
		} catch (Exception e) {
			System.out.println("[error] : " + e.getMessage());
			return false;
		}
	}
	
	private String keygen(String mail) {
		return "mutual-funds-" + mail + "-otp";
	}

	private String generateOTP() {
		return new DecimalFormat("0000").format(new Random().nextInt(9999));
	}

	public Boolean verifyOtp(otpModel input) {
		String key = keygen(input.getEmail());
		Optional<otpEntity> otpver_temp = otpsave.findByKey(key);
		if (otpver_temp.isPresent()) {
			otpEntity otpval = otpver_temp.get();
			if (otpval.getOtpval() != null && otpval.getOtpval().equals(input.getOtp())) {
				otpval.setOtpval(null);
				otpsave.save(otpval);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}
