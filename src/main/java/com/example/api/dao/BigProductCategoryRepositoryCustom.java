package com.example.api.dao;

import java.util.Date;

import com.example.api.model.BigCategory;

public interface BigProductCategoryRepositoryCustom {

	long getMaxId();
	long update(String empNo, String fullName, Date hireDate);
	BigCategory getById(long id);
	boolean delete(String id);
	boolean update(BigCategory bigcategory,String UserName);
	

}