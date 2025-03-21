package com.mutual.funds.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mutual.funds.entity.SubCategoryEntity;
import com.mutual.funds.service.CategoryService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin(origins = "*" , allowedHeaders = "*")
@RestController
@RequestMapping(value = "api/type")
public class CategoryController {
	
	@Autowired
	private CategoryService cat;

    @GetMapping(value = "/getCategotries")
    public ResponseEntity<?> getMainCategories() {
        List<Map> respVal = cat.getAllCategories();
        Map<String,Object> response = new HashMap<String,Object>(); 
        if(respVal.size() != 0) {
        	response.put("data", respVal);
			ResponseEntity<?> resp = new ResponseEntity<>(response, HttpStatus.OK);
			return resp;
		}
		else {
			response.put("message", "no data in DB");
			ResponseEntity<?> resp = new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
			return resp;
		}
    }
    
    @GetMapping(value = "/getSubCategoryDetails")
    public ResponseEntity<?> subCatDetails(@RequestParam Integer subCatId){
    	SubCategoryEntity respVal = cat.getSubCatDetails(subCatId);
    	Map<String,Object> response = new HashMap<String,Object>(); 
    	if(respVal != null) {
    		response.put("data", respVal);
    		ResponseEntity<?> resp = new ResponseEntity<>(response, HttpStatus.OK);
			return resp;
    	}
    	else {
    		response.put("message", "no data in DB");
			ResponseEntity<?> resp = new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
			return resp;
		}
    }
    

}
