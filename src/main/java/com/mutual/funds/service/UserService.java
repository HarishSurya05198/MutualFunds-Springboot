package com.mutual.funds.service;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mutual.funds.entity.UserEntity;
import com.mutual.funds.model.UserModel;
import com.mutual.funds.repository.UserRepo;
import com.mutual.funds.security.jwtSecurity;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	
	private final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Value("${spring.mail.username}")
	private String fromMail;
	
	@Autowired
	private UserRepo rep;
	
	@Autowired
	private jwtSecurity sec;
	
	public List<UserEntity> getAllUsers(){
		List<UserEntity> getVal = rep.findAll();
		
		return getVal;
	}
	
	
	public UserEntity getUserbyEmail(String email) {
		try {
			UserEntity user = rep.findByEmail(email);
			byte[] actualByte = Base64.getDecoder().decode(user.getPassword());
			String actualPassword = new String(actualByte);
			user.setPassword(actualPassword);
			return user;
		}
		catch(Exception e) {
			return null;
		}		
	}
	
	public Map<String,Object> editProfile(UserModel u) {
		try {
			Optional<UserEntity> temp = rep.findById(u.getId());
			if(temp.isPresent()) {
				UserEntity user = temp.get();
				user.setEmail(u.getEmail());
				user.setName(u.getName());
				String encryptedPassword = Base64.getEncoder().encodeToString(u.getPassword().getBytes());
				user.setPassword(encryptedPassword);
				user.setPhone_number(u.getPhone());
				rep.save(user);
				Map<String,Object> obj = new HashMap<>();
				obj.put("data", user);
				return obj;
			}
			else {
				return null;
			}
		}
		catch(Exception e) {
			return null;
		}
	}
	
	@Transactional(rollbackOn = NotFoundException.class)
	public Boolean addUser(UserModel u) throws NotFoundException {
		try {
			UserEntity users = new UserEntity();
			logger.info("check value here : "+u.getName());
			UserEntity check = rep.findByEmail(u.getEmail());
			//logger.info("check value here 2 : "+check);
			if (check == null) {
				users.setName(u.getName());
				users.setEmail(u.getEmail());
				String encryptedPassword = Base64.getEncoder().encodeToString(u.getPassword().getBytes());
				users.setPassword(encryptedPassword);
				users.setPhone_number(u.getPhone());
				//logger.info("check enc pass "+users.getPassword());
				rep.save(users);
				return true;
			} else {
				throw new NotFoundException();
			}
		} catch (NotFoundException e) {
			//logger.error(e.getMessage());
			return false;
		}
	}
	
	public Map<String,Object> loginUser(UserModel u) {
		try {
			UserEntity users = new UserEntity();
			UserEntity resp = null;
			users.setEmail(u.getEmail());
			resp = rep.findByEmail(users.getEmail());
			byte[] actualByte = Base64.getDecoder().decode(resp.getPassword());
			String actualPassword = new String(actualByte);
			Map<String,Object> response = new HashMap<String,Object>();
			//logger.info("check given pass "+u.getPassword());
			//logger.info("check enc pass "+actualPassword);
			if(!actualPassword.equals(u.getPassword())) {
				response.put("message", "Password Mismatch !");
				return response;
			}
			else {
				response.put("data", resp);
				String token = sec.createJwtToken(resp);
				response.put("token", token);
				return response;
			}
		} catch (Exception e) {
			return null;
		}
	}


}
