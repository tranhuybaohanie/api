package com.example.api.dao;


import java.util.Date;
import java.util.List;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.api.model.ProductCategory;

//This is an Interface.
//No need Annotation here
public interface ProductCategoryRepository extends MongoRepository<ProductCategory, Long> { // Long: Type of Employee ID.

	ProductCategory findByCode(String code);
	ProductCategory findById(long id);
	List<ProductCategory> findByBigCategoryId(long id);
   List<ProductCategory> findByNameViLike(String name_vi);
   List<ProductCategory> findByNameEnLike(String name_en);

   List<ProductCategory> findByCreateDateGreaterThan(Date createDate);

   // Supports native JSON query string
//   @Query("{fullName:'?0'}")
//   List<BigCategory> findCustomByFullName(String fullName);

}
