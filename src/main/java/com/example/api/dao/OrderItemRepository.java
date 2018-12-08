package com.example.api.dao;


import java.util.Date;
import java.util.List;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.api.model.Order;
import com.example.api.model.OrderItem;



//This is an Interface.
//No need Annotation here
public interface OrderItemRepository extends MongoRepository<OrderItem, Long> { // Long: Type of Employee ID.

	OrderItem findByCode(String code);
	OrderItem findById(long id);

   List<OrderItem> findByCreateDateGreaterThan(Date createDate);

   // Supports native JSON query string
//   @Query("{fullName:'?0'}")
//   List<BigCategory> findCustomByFullName(String fullName);

}
