package com.example.api.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.example.api.model.Order;
import com.example.api.model.OrderItem;
import com.example.api.model.Product;
import com.example.api.utilities.DateTime;
import com.mongodb.client.result.UpdateResult;

@Repository
public class OrderItemRepositoryCustomImpl implements OrderItemRepositoryCustom {

	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	OrderItemRepository Repository;

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
		OrderItem maxObject = (OrderItem) mongoTemplate.findOne(query, OrderItem.class);
		if (maxObject == null) {
			return 0L;
		}
		return maxObject.getId();
	}


	@Override
	public boolean delete(String id) {
		try {
			this.mongoTemplate.remove(new Query(Criteria.where("id").is(Long.parseLong(id))), OrderItem.class);
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	@Override
	public OrderItem getById(long id) {
		return this.Repository.findById(id);
	}

	@Override
	public boolean update(OrderItem item,String id) {
		try {
			OrderItem updateItem = getById(item.getId());
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
			updateItem.setModifiedDate(DateTime.getCurrentDateTime());
			updateItem.setCurrency(item.getCurrency());
			updateItem.setNutritionalValue(item.getNutritionalValue());
			updateItem.setPrice(item.getPrice());
			updateItem.setPromotionPrice(item.getPromotionPrice());
			updateItem.setProductCategoryId(item.getProductCategoryId());
			updateItem.setIngredient(item.getIngredient());
			updateItem.setImgMoreUrl(item.getImgMoreUrl());
			
			this.mongoTemplate.save(updateItem);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}