package com.example.api.exception;


import java.util.Date;
import java.util.List;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.api.model.BigCategory;
import com.example.api.model.Employee;

//This is an Interface.
//No need Annotation here
public interface BigCategoryRepository extends MongoRepository<BigCategory, Long> { // Long: Type of Employee ID.

	BigCategory findByCode(String code);
	BigCategory findById(long id);
   List<BigCategory> findByNameViLike(String name_vi);
   List<BigCategory> findByNameEnLike(String name_en);

   List<BigCategory> findByCreateDateGreaterThan(Date createDate);

   // Supports native JSON query string
//   @Query("{fullName:'?0'}")
//   List<BigCategory> findCustomByFullName(String fullName);

}
