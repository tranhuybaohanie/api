package com.example.api.dao;


import java.util.Date;
import java.util.List;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.api.model.Order;



//This is an Interface.
//No need Annotation here
public interface OrderRepository extends MongoRepository<Order, Long> { // Long: Type of Employee ID.

	Order findByCode(String code);
	Order findById(long id);

   List<Order> findByCreateDateGreaterThan(Date createDate);

   // Supports native JSON query string
//   @Query("{fullName:'?0'}")
//   List<BigCategory> findCustomByFullName(String fullName);

}
