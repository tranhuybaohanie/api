package com.example.api.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.api.dao.EmployeeRepository;
import com.example.api.dao.EmployeeRepositoryCustom;
import com.example.api.dao.OrderItemRepository;
import com.example.api.dao.OrderItemRepositoryCustom;
import com.example.api.dao.OrderRepository;
import com.example.api.dao.OrderRepositoryCustom;
import com.example.api.dao.ProductCategoryRepository;
import com.example.api.dao.ProductCategoryRepositoryCustom;
import com.example.api.dao.ProductRepository;
import com.example.api.dao.ProductRepositoryCustom;
import com.example.api.model.BigCategory;
import com.example.api.model.Employee;
import com.example.api.model.Order;
import com.example.api.model.OrderItem;
import com.example.api.model.Order_OrderItem;
import com.example.api.model.Product;
import com.example.api.model.ProductCategory;
import com.example.api.model.UploadFileResponse;
import com.example.api.utilities.DateTime;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Controller

@RequestMapping("/order")
public class OrderController {


	@Autowired
	private OrderRepositoryCustom RepositoryCustom;
	
	@Autowired
	private OrderItemRepositoryCustom ItemRepositoryCustom;

	@Autowired
	private OrderRepository Repository;
	
	@Autowired
	private OrderItemRepository ItemRepository;

	@Autowired
	private final SimpMessagingTemplate template;

	@Autowired
	OrderController(SimpMessagingTemplate template) {
		this.template = template;
	}

	@MessageMapping("/update/order")
	public void onReceivedMesage(String message) {
		System.out.println("send: " + message);
		this.template.convertAndSend("/order/snapshot",
				// new SimpleDateFormat("HH:mm:ss").format(new Date()) + "- " + message
				this.Repository.findAll());
	}



	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
	public HashMap<String, Object> Insert(@RequestBody Order_OrderItem item, HttpSession httpSession) {
	
		
		Order order =item.getOrder();
		List<OrderItem> orderItem =item.getOrderItem();
		
		try {
			
			long id = this.RepositoryCustom.getMaxId() + 1;
			order.setId(id);
			order.setCode(DateTime.getCurrentDateTimeForID() + "S" + id);
			order.setDeliveredState(false);
			order.setReceivedState(false);
			order.setDeliveringState(false);
			order.setPhone(order.getPhone());
			order.setAddress(order.getAddress());
			order.setFullName(order.getFullName());
			order.setCreateDate(DateTime.getCurrentDateTime());
			order.setUser(httpSession.getAttribute("auth").toString());
			order.setStatus(true);
			this.Repository.insert(order);
			for(OrderItem itemi : orderItem) {
				OrderItem itemOr= new OrderItem();
			long idItem = this.ItemRepositoryCustom.getMaxId() + 1;
			itemOr.setOrderId(id);
			itemOr.setCode(itemi.getId());
			itemOr.setId(idItem);
			itemOr.setDescriptionVi(itemi.getDescriptionVi());
			itemOr.setDescriptionEn(itemi.getDescriptionEn());
			itemOr.setBigCategoryId(itemi.getBigCategoryId());
			itemOr.setNameEn(itemi.getNameEn());
			itemOr.setNameVi(itemi.getNameVi());
			itemOr.setStatus(itemi.getStatus());
			itemOr.setImgUrl(itemi.getImgUrl());
			itemOr.setMetaKeyword(itemi.getMetaKeyword());
			itemOr.setMetaTitle(itemi.getMetaTitle());
			itemOr.setMetaDiscription(itemi.getMetaDiscription());
			itemOr.setCreateDate(DateTime.getCurrentDateTime());
			itemOr.setCurrency(itemi.getCurrency());
			itemOr.setNutritionalValue(itemi.getNutritionalValue());
			itemOr.setPrice(itemi.getPrice());
			itemOr.setPromotionPrice(itemi.getPromotionPrice());
			itemOr.setProductCategoryId(itemi.getProductCategoryId());
			itemOr.setIngredient(itemi.getIngredient());
			itemOr.setImgMoreUrl(itemi.getImgMoreUrl());
			itemOr.setQuantity(itemi.getQuantity());
			this.ItemRepository.insert(itemOr);
			}
			
			HashMap<String, Object> map = new HashMap<>();
			map.put("result", true);
			this.template.convertAndSend("/order/snapshot", this.Repository.findAll());
			return map;
		} catch (Exception e) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("result", false);
			map.put("error", e);
			return map;
		}

		
	}

	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.PUT, produces = "application/json")
	public HashMap<String, Object> update(@RequestBody Product item, HttpSession httpSession) {

//		boolean result = this.RepositoryCustom.update(item,
//				httpSession.getAttribute("auth").toString());
//		if (result) {
//			HashMap<String, Object> map = new HashMap<>();
//			map.put("result", true);
//			this.template.convertAndSend("/product/snapshot", this.Repository.findAll());
//			return map;
//		} else {
			HashMap<String, Object> map = new HashMap<>();
			map.put("result", false);
			return map;
//		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> Delete(@PathVariable("id") String id) throws IOException {

		// TODO process the file here
		if (this.RepositoryCustom.delete(id)) {
			this.template.convertAndSend("/product/snapshot", this.Repository.findAll());
			return ResponseEntity.ok().body("ok");
		} else {
			return ResponseEntity.badRequest().body("fail");
		}

	}

//	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
//	public ResponseEntity<Product> GetByID(@PathVariable("id") String id) throws IOException {
//
//		// TODO process the file here
//
//		return ResponseEntity.ok().body(this.RepositoryCustom.getById(Long.parseLong(id)));
//
//	}

//	@ResponseBody
//	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
//
//	public List<Product> showAll() {
//		List<Product> item = this.Repository.findAll();
//		return item;
//	}


}