package com.mutual.funds.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mutual.funds.entity.CategoryEntity;

public interface CategoryRepo extends JpaRepository<CategoryEntity, Integer>{
	
	@Query(value = "SELECT category.id as cat_id, category.name as cat_name, subcategory.id, subcategory.name, subcategory.definition FROM category RIGHT JOIN subcategory ON category.id = subcategory.cat_id ORDER BY category.id;", nativeQuery = true)
	List<Map> fetchCategory(); 

}
