package com.example.api.exception;

import java.util.Date;

import com.example.api.model.BigCategory;

public interface BigCategoryRepositoryCustom {

	long getMaxId();
	long update(String empNo, String fullName, Date hireDate);
	BigCategory getById(long id);
	boolean delete(String id);
	boolean update(BigCategory bigcategory,String UserName);
	

}