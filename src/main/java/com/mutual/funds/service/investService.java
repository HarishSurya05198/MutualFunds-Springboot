package com.mutual.funds.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.mutual.funds.entity.SubCategoryEntity;
import com.mutual.funds.entity.investEntity;
import com.mutual.funds.model.investModel;
import com.mutual.funds.repository.SubCategoryRepo;
import com.mutual.funds.repository.UserRepo;
import com.mutual.funds.repository.investRepo;

import jakarta.transaction.Transactional;

@Service
public class investService {
	
	private final Logger logger = LoggerFactory.getLogger(investService.class);
	
	@Autowired
	private SubCategoryRepo subcat;
	
	@Autowired
	private UserRepo user;
	
	@Autowired
	private investRepo book;

	@Transactional(rollbackOn = NotFoundException.class)
	public Boolean addbooking(investModel b) throws NotFoundException {
		investEntity boo = new investEntity();

		Optional<SubCategoryEntity> cat_val = subcat.findById((Integer) b.getSub_cat_id());
		Optional<com.mutual.funds.entity.UserEntity> user_val = user.findById((Integer) b.getUser_id());
		try {
			if (cat_val.isPresent() && user_val.isPresent()) {
				boo.setScheme(cat_val.get());
				boo.setUser(user_val.get());
				boo.setDate(b.getDate());
				boo.setTotal_amount(b.getTotal_amount());
				book.save(boo);
				return true;
			} else {
				throw new NotFoundException();
			}
		}
		catch(NotFoundException e) {
			logger.error("not found "+e.getMessage());
			return false;
		}
	}
	
	public List<Map> getInvestList(investModel u){
		List<Map> resp = new ArrayList<>();
		List<investEntity> apiVal = new ArrayList<>();
		if(u.getUser_id().equals(0)) {
			apiVal = book.findAll();
		}
		else {
			apiVal = book.findByUserId(u.getUser_id());
		}
		for(investEntity temp :apiVal) {
			Map<String,Object> val = new HashMap<>();
			val.put("id",temp.getId());
			val.put("user_name", temp.getUser().getName());
			//val.put("user_id", temp.getUser().getId());
			val.put("scheme_name", temp.getScheme().getName());
			//val.put("scheme_id", temp.getScheme().getId());
			val.put("date",temp.getDate());
			val.put("amount", temp.getTotal_amount());
			resp.add(val);
		}
		return resp;
	}
	
	@Transactional(rollbackOn = NotFoundException.class)
	public List<Map> searchByString(String s) throws NotFoundException{
		logger.info("search keyword : "+s);
		try {
			List<Map> resp = book.searchByKeyword(s);
			if(resp.size() != 0) {
				return resp;
			}
			else {
				throw new NotFoundException();
			}
		}
		catch(NotFoundException e) {
			logger.info("no matching values in DB for the given string : "+s);
			return null;
		}
	}
}
