package com.mutual.funds.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mutual.funds.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Integer>{

	
	public UserEntity findByEmail(String mail);
}
