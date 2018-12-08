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
import com.example.api.utilities.DateTime;
@Repository
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	OrderRepository Repository;

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
		Order maxObject = (Order) mongoTemplate.findOne(query, Order.class);
		if (maxObject == null) {
			return 0L;
		}
		return maxObject.getId();
	}


	@Override
	public boolean delete(String id) {
		try {
			this.mongoTemplate.remove(new Query(Criteria.where("id").is(Long.parseLong(id))), Order.class);
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	@Override
	public Order getById(long id) {
		return this.Repository.findById(id);
	}

	@Override
	public boolean update(Order item,String id) {
		try {
			Order updateItem = getById(item.getId());
			updateItem.setPhone(item.getPhone());
			updateItem.setAddress(item.getAddress());
			updateItem.setFullName(item.getFullName());
			updateItem.setDeliveringState(item.getDeliveringState());
			updateItem.setReceivedState(item.getReceivedState());
			updateItem.setDeliveredState(item.getDeliveredState());
			updateItem.setStatus(item.getStatus());
			updateItem.setCreateDate(DateTime.getCurrentDateTime());
			this.mongoTemplate.save(updateItem);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}