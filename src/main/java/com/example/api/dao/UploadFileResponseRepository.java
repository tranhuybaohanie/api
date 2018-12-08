package com.example.api.dao;


import java.util.Date;
import java.util.List;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.api.model.BigCategory;
import com.example.api.model.Employee;
import com.example.api.model.UploadFileResponse;

//This is an Interface.
//No need Annotation here
public interface UploadFileResponseRepository extends MongoRepository<UploadFileResponse, Long> { // Long: Type of Employee ID.

	UploadFileResponse findById(String code);

//   List<BigCategory> findByNameViLike(String name_vi);
//   List<BigCategory> findByNameEnLike(String name_en);
//
//   List<BigCategory> findByCreateDateGreaterThan(Date createDate);

   // Supports native JSON query string
//   @Query("{fullName:'?0'}")
//   List<BigCategory> findCustomByFullName(String fullName);

}
