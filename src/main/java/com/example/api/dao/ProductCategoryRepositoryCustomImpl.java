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
import com.example.api.model.ProductCategory;
import com.example.api.model.UploadFileResponse;
import com.example.api.utilities.DateTime;
import com.mongodb.client.result.UpdateResult;

@Repository
public class ProductCategoryRepositoryCustomImpl implements ProductCategoryRepositoryCustom {

	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	ProductCategoryRepository Repository;

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
		ProductCategory maxObject = (ProductCategory) mongoTemplate.findOne(query, ProductCategory.class);
		if (maxObject == null) {
			return 0L;
		}
		return maxObject.getId();
	}


	@Override
	public boolean delete(String id) {
		try {
			this.mongoTemplate.remove(new Query(Criteria.where("id").is(Long.parseLong(id))), ProductCategory.class);
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	@Override
	public ProductCategory getById(long id) {
		return this.Repository.findById(id);
	}

	@Override
	public boolean update(ProductCategory item, String userName) {
		try {
			ProductCategory updateItem = getById(item.getId());
			updateItem.setDescriptionVi(item.getDescriptionVi());
			updateItem.setDescriptionEn(item.getDescriptionEn());
			updateItem.setNameEn(item.getNameEn());
			updateItem.setNameVi(item.getNameVi());
			updateItem.setStatus(item.getStatus());
			updateItem.setImgUrl(item.getImgUrl());
			updateItem.setBigCategoryId(item.getBigCategoryId());
			updateItem.setMetaKeyword(item.getMetaKeyword());
			updateItem.setMetaTitle(item.getMetaTitle());
			updateItem.setMetaDiscription(item.getMetaDiscription());
			updateItem.setModifiedBy(userName);
			updateItem.setModifiedDate(DateTime.getCurrentDateTime());
			this.mongoTemplate.save(updateItem);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}