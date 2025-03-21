package com.mutual.funds.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mutual.funds.entity.SubCategoryEntity;
import com.mutual.funds.repository.CategoryRepo;
import com.mutual.funds.repository.SubCategoryRepo;

@Service
public class CategoryService {
	
	private final Logger logger = LoggerFactory.getLogger(CategoryService.class);
	
	@Autowired
	private CategoryRepo cat;
	
	@Autowired
	private SubCategoryRepo subCat;

    public List<Map> getAllCategories(){
    	List<Map> resp = cat.fetchCategory();
    	List<Map> finalList = new ArrayList<>();
    	resp.forEach(item->{
    		
    		Map<String, Object> finalVal = new HashMap<String,Object>();
    		finalVal.put("CategoryName", item.get("cat_name"));
    		finalVal.put("CategoryId", item.get("cat_id"));
    		
    		finalList.add(finalVal);	
    		
    	});
    	List<Map> finalResp = finalList.stream()
			    .distinct()
			    .collect(Collectors.toList());
    	
    		
    		finalResp.forEach(j->{
    			List<Map> subcat = new ArrayList<>();
    			resp.stream().forEach(item->{
    			Map<String, Object> subs = new HashMap<String,Object>();
    			if(j.get("CategoryId").equals(item.get("cat_id"))){
    				subs.put("SubCategoryName", item.get("name"));
    				subs.put("SubCategoryId", item.get("id"));
    				subcat.add(subs);
    			}
    		});
    		j.put("SubCategories", subcat);
    		
    	});
    	//logger.info("check resp from db : "+resp);
    	return finalResp;
//    	List<CategoryEntity> catList = cat.findAll();
//    	List<SubCategoryEntity> subCatList = subCat.findAll();
//    	List<Map> finalList = new ArrayList<>();
//    	for(int i=0;i<catList.size();i++) {
//    		Map<String, Object> finalVal = new HashMap<String,Object>();
//    		finalVal.put("CategoryName", catList.get(i).getName());
//    		finalVal.put("CategoryId", catList.get(i).getId());
//    		List<Map> SubCatsList = new ArrayList<>();
//    		for(int j=0;j<subCatList.size();j++) {
//    			Map<String, Object> subs = new HashMap<String,Object>();
//    			if(subCatList.get(j).getCat().getId() == catList.get(i).getId()) {
//    				subs.put("SubCategoryName", subCatList.get(j).getName());
//    				subs.put("SubCategoryId", subCatList.get(j).getId());
//    				SubCatsList.add(subs);
//    			}
//    		}
//    		finalVal.put("SubCategories", SubCatsList);
//    		finalList.add(finalVal);		
//    	}
////    	logger.info("check temp val cat name - "+finalList);
//        return finalList;
    }
    
    public SubCategoryEntity getSubCatDetails(Integer subId) {
    	Optional<SubCategoryEntity> checkVal = subCat.findById(subId);
    	if(checkVal.isPresent()) {
    		SubCategoryEntity respVal = checkVal.get();
    		return respVal;
    	}
    	else {
    		return null;
    	}
    }
}
