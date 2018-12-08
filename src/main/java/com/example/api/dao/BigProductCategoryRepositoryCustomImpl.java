package com.example.api.dao;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.example.api.model.BigCategory;
import com.example.api.model.UploadFileResponse;
import com.example.api.utilities.DateTime;
import com.mongodb.client.result.UpdateResult;

@Repository
public class BigProductCategoryRepositoryCustomImpl implements BigProductCategoryRepositoryCustom {

	@Autowired
	MongoTemplate mongoTemplate;
//	@Autowired
	BigProductCategoryRepository bigcategoryRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.api.dao.UserStaffRepositoryCustom#getMaxUserStaffId()
	 */
	@Override
	public long getMaxId() {
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "id"));
		query.limit(1);
		BigCategory maxObject = (BigCategory) mongoTemplate.findOne(query, BigCategory.class);
		if (maxObject == null) {
			return 0L;
		}
		return maxObject.getId();
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
	public boolean delete(String id) {
		try {
			this.mongoTemplate.remove(new Query(Criteria.where("id").is(Long.parseLong(id))), BigCategory.class);
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	@Override
	public BigCategory getById(long id) {
		return this.bigcategoryRepository.findById(id);
		
	}

	@Override
	public boolean update(BigCategory bigCategoryPost, String userName) {
		try {
			BigCategory bigCategory = getById(bigCategoryPost.getId());
			bigCategory.setDescriptionVi(bigCategoryPost.getDescriptionVi());
			bigCategory.setDescriptionEn(bigCategoryPost.getDescriptionEn());
			bigCategory.setNameEn(bigCategoryPost.getNameEn());
			bigCategory.setNameVi(bigCategoryPost.getNameVi());
			bigCategory.setStatus(bigCategoryPost.getStatus());
			bigCategory.setImgUrl(bigCategoryPost.getImgUrl());
			bigCategory.setMetaKeyword(bigCategoryPost.getMetaKeyword());
			bigCategory.setMetaTitle(bigCategoryPost.getMetaTitle());
			bigCategory.setMetaDiscription(bigCategoryPost.getMetaDiscription());
			bigCategory.setModifiedBy(userName);
			bigCategory.setModifiedDate(DateTime.getCurrentDateTime());
			this.mongoTemplate.save(bigCategory);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}