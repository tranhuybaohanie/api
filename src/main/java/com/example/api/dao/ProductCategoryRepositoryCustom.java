package com.example.api.dao;

import java.util.Date;

import com.example.api.model.BigCategory;
import com.example.api.model.ProductCategory;

public interface ProductCategoryRepositoryCustom {

	long getMaxId();
	ProductCategory getById(long id);
	boolean delete(String id);
	boolean update(ProductCategory productCategory,String UserName);
	

}