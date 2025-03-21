package com.mutual.funds.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mutual.funds.model.UserModel;
import com.mutual.funds.entity.UserEntity;
import com.mutual.funds.service.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="api/user")
public class UserController {
	
	@Autowired
	private UserService user;

	@GetMapping(value = "/allUsers")
	public ResponseEntity<?> getAllUsers(){
		
		List<UserEntity> userResp = user.getAllUsers();
		if(userResp.size() != 0) {
			ResponseEntity<?> resp = new ResponseEntity<>(userResp, HttpStatus.OK);
			return resp;
		}
		else {
			ResponseEntity<?> resp = new ResponseEntity<>("no data in DB",HttpStatus.NOT_FOUND);
			return resp;
		}
	}
	
	@GetMapping(value = "/getByEmail")
	public ResponseEntity<?> getUserByEmail(@RequestParam String email){
		
		UserEntity userResp = user.getUserbyEmail(email);
		Map<String,Object> response = new HashMap<String,Object>(); 
		if(userResp != null) {
			response.put("data", userResp);
			ResponseEntity<?> resp = new ResponseEntity<>(response, HttpStatus.OK);
			return resp;
		}
		else {
			response.put("message", "user not found based on the email provided");
			ResponseEntity<?> resp = new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
			return resp;
		}
	}
	
	@PutMapping(value = "/update")
	public ResponseEntity<?> updateUser(@RequestBody UserModel u){
		//boolean respVal;
		Map<String, Object> respVal = user.editProfile(u);
		//respVal = user.editProfile(u);
		//Map<String,Object> response = new HashMap<String,Object>(); 
		if(respVal != null) {
			respVal.put("message", "user updated successfully!");
			ResponseEntity<?> resp = new ResponseEntity<>(respVal, HttpStatus.OK);
			return resp;
		}
		else {
			respVal.put("message", "updation failed");
			ResponseEntity<?> resp = new ResponseEntity<>(respVal, HttpStatus.OK);
			return resp;
		}
	}
	
	@PostMapping(value = "/register")
	public ResponseEntity<?> addUser(@RequestBody UserModel u) throws NotFoundException{
		boolean respVal;
		respVal = user.addUser(u);
		Map<String,Object> response = new HashMap<String,Object>(); 
		if(respVal == true) {
			response.put("message", "user added successfully!");
			ResponseEntity<?> resp = new ResponseEntity<>(response, HttpStatus.OK);
			return resp;
		}
		else {
			response.put("message", "registration failed");
			ResponseEntity<?> resp = new ResponseEntity<>(response, HttpStatus.OK);
			return resp;
		}
	}
	
	@PostMapping(value="login")
	public ResponseEntity<?> loginUser(@RequestBody UserModel u){
		Map<String,Object> userResp = user.loginUser(u);
		if(userResp != null) {
			ResponseEntity<?> resp = new ResponseEntity<>(userResp, HttpStatus.OK);
			return resp;
		}
		else {
			ResponseEntity<?> resp = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resp;
		}
	}
}
