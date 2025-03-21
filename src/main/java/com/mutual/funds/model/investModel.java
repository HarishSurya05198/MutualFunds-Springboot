package com.mutual.funds.model;

import java.sql.Date;

import jakarta.persistence.Id;

import org.springframework.lang.NonNull;

public class investModel {
	
	@Id
	private Integer id;
	
	private Date date;
	
	@NonNull
	private Integer user_id;
	
	@NonNull
	private Integer sub_cat_id;
	
	private Integer total_amount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getSub_cat_id() {
		return sub_cat_id;
	}

	public void setSub_cat_id(Integer sub_cat_id) {
		this.sub_cat_id = sub_cat_id;
	}

	public Integer getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(Integer total_amount) {
		this.total_amount = total_amount;
	}

	
	
}
