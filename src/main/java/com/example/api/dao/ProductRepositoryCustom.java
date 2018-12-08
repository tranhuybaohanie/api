package com.example.api.dao;

import java.util.Date;

import com.example.api.model.BigCategory;
import com.example.api.model.Product;
import com.example.api.model.ProductCategory;

public interface ProductRepositoryCustom {

	long getMaxId();
	Product getById(long id);
	boolean delete(String id);
	boolean update(Product product,String UserName);
	

}