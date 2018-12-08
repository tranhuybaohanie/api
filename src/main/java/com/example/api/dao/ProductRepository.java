package com.example.api.dao;


import java.util.Date;
import java.util.List;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.api.model.BigCategory;
import com.example.api.model.Employee;
import com.example.api.model.Product;
import com.example.api.model.ProductCategory;

//This is an Interface.
//No need Annotation here
public interface ProductRepository extends MongoRepository<Product, Long> { // Long: Type of Employee ID.

	Product findByCode(String code);
	Product findById(long id);
   List<Product> findByNameViLike(String name_vi);
   List<Product> findByNameEnLike(String name_en);

   List<Product> findByCreateDateGreaterThan(Date createDate);

   // Supports native JSON query string
//   @Query("{fullName:'?0'}")
//   List<BigCategory> findCustomByFullName(String fullName);

}
