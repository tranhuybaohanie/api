package com.example.api.dao;

import java.util.Date;
import java.util.List;

import com.example.api.model.Order;
import com.example.api.model.OrderItem;



public interface OrderItemRepositoryCustom {

	long getMaxId();
	OrderItem getById(long id);
	boolean delete(String id);
	boolean update(OrderItem order,String id);
	

}