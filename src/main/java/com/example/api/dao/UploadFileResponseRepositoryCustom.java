package com.example.api.dao;


import java.util.Date;
import java.util.List;

import com.example.api.model.UploadFileResponse;

public interface UploadFileResponseRepositoryCustom {

	int getMaxCode();
	long update(String empNo, String fullName, Date hireDate);
	boolean add(UploadFileResponse image);
	boolean delete(String id);
	UploadFileResponse findById(String id);
	List<UploadFileResponse> getAll();

}