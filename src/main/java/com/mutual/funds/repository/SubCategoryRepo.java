package com.mutual.funds.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

import com.mutual.funds.entity.CategoryEntity;
import com.mutual.funds.entity.SubCategoryEntity;

public interface SubCategoryRepo extends JpaRepository<SubCategoryEntity, Integer>{
	
//	@NativeQuery("select * from")
	
	@Query(value = "SELECT category.id, category.name, subcategory.id, subcategory.name FROM subcategory RIGHT JOIN category ON category.id = subcategory.cat_id ORDER BY category.id;", nativeQuery = true)
	List<SubCategoryEntity> fetchCategory(); 

}
