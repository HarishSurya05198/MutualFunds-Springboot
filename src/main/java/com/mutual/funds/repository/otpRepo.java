package com.mutual.funds.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mutual.funds.entity.otpEntity;

public interface otpRepo extends JpaRepository<otpEntity, Integer> {

	Optional<otpEntity> findByKey(String key);
}
