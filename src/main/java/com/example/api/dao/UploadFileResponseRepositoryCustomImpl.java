package com.example.api.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.example.api.model.BigCategory;
import com.example.api.model.UploadFileResponse;
import com.mongodb.client.result.UpdateResult;

@Repository
public class UploadFileResponseRepositoryCustomImpl implements UploadFileResponseRepositoryCustom {

	@Autowired
	MongoTemplate mongoTemplate;
@Autowired 
UploadFileResponseRepository uploadFileResponseRepository;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.api.dao.UserStaffRepositoryCustom#getMaxUserStaffId()
	 */
	@Override
	public int getMaxCode() {
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "code"));
		query.limit(1);
		UploadFileResponse maxObject = (UploadFileResponse) mongoTemplate.findOne(query, UploadFileResponse.class);
		if (maxObject == null) {
			return 0;
		}
		return maxObject.getCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.api.dao.UserStaffRepositoryCustom#updateUserStaff(java.lang.
	 * String, java.lang.String, java.util.Date)
	 */
	@Override
	public long update(String empNo, String fullName, Date hireDate) {
		Query query = new Query(Criteria.where("empNo").is(empNo));
		Update update = new Update();
		update.set("fullName", fullName);
		update.set("hireDate", hireDate);

		UpdateResult result = this.mongoTemplate.updateFirst(query, update, BigCategory.class);

		if (result != null) {
			return result.getModifiedCount();
		}

		return 0;
	}

	@Override
	public boolean add(UploadFileResponse image) {

		try {
			this.mongoTemplate.save(image);
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	@Override
	public boolean delete(String id) {
		try {
			this.mongoTemplate.remove(new Query(Criteria.where("id").is(id)), UploadFileResponse.class);
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	@Override
	public UploadFileResponse findById(String id) {
		return uploadFileResponseRepository.findById(id);
	}

	@Override
	public List<UploadFileResponse> getAll() {
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "code"));
//		query.limit(1);
		List<UploadFileResponse> maxObject = (List<UploadFileResponse>) mongoTemplate.find(query, UploadFileResponse.class);
		
		return maxObject;
	}

}