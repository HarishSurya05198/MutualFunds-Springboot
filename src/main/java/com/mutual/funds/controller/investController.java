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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mutual.funds.model.investModel;
import com.mutual.funds.service.investService;

@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping(value = "api/invest")
public class investController {
	
	@Autowired
	private investService invest;
	
	@PostMapping(value = "/bookInvestment")
	public ResponseEntity<?> bookInvestment(@RequestBody investModel b) throws NotFoundException{
		boolean resp = invest.addbooking(b);
		Map<String,Object> res = new HashMap<String,Object>(); 
		if(resp == true) {
			res.put("message", "Invested successfully");
			ResponseEntity<?> response = new ResponseEntity<>(res, HttpStatus.OK);
			return response;
		}
		else {
			res.put("message", "Investment failed");
			ResponseEntity<?> response = new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
			return response;
		}
	}
	
	@PostMapping(value = "/getInvestmentList")
	public ResponseEntity<?> getInvestment(@RequestBody investModel u, @RequestHeader(name = "Authorization" , required = true) String token){
		List<Map> resp = invest.getInvestList(u);
		Map<String,Object> response = new HashMap<String,Object>(); 
		if(token != null) {
			if(resp.size() != 0) {
	        	response.put("data", resp);
				ResponseEntity<?> respVal = new ResponseEntity<>(response, HttpStatus.OK);
				return respVal;
			}
			else {
				response.put("message", "no data in DB");
				ResponseEntity<?> respVal = new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
				return respVal;
			}
		}
		else {
			response.put("message", "please login to view details");
			ResponseEntity<?> respVal = new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
			return respVal;
		}
        
	}
	
	@GetMapping(value="/search")
	public ResponseEntity<?> searchByString(@RequestParam String keyword ,  @RequestHeader(name = "Authorization" , required = true) String token) throws NotFoundException{
		List<Map> resp = invest.searchByString(keyword);
		Map<String,Object> response = new HashMap<String,Object>(); 
		if(token != null) {
			if(resp != null) {
	        	response.put("data", resp);
				ResponseEntity<?> respVal = new ResponseEntity<>(response, HttpStatus.OK);
				return respVal;
			}
			else {
				response.put("message", "No matching records found for the given string "+keyword);
				ResponseEntity<?> respVal = new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
				return respVal;
			}
		}
		else {
			response.put("message", "please login to view details");
			ResponseEntity<?> respVal = new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
			return respVal;
		}
	}


}
