package com.example.api.dao;

import java.util.Date;
import java.util.List;

import com.example.api.model.Order;



public interface OrderRepositoryCustom {

	long getMaxId();
	Order getById(long id);
	boolean delete(String id);
	boolean update(Order order,String id);
	

}