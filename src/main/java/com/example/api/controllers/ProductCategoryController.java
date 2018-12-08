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
import com.example.api.dao.ProductCategoryRepository;
import com.example.api.dao.ProductCategoryRepositoryCustom;
import com.example.api.model.BigCategory;
import com.example.api.model.Employee;
import com.example.api.model.ProductCategory;
import com.example.api.model.UploadFileResponse;
import com.example.api.utilities.DateTime;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Controller

@RequestMapping("/product-category")
public class ProductCategoryController {


	@Autowired
	private ProductCategoryRepositoryCustom RepositoryCustom;

	@Autowired
	private ProductCategoryRepository Repository;

	@Autowired
	private final SimpMessagingTemplate template;

	@Autowired
	ProductCategoryController(SimpMessagingTemplate template) {
		this.template = template;
	}

	@MessageMapping("/update/productcategory")
	public void onReceivedMesage(String message) {
		System.out.println("send: " + message);
		this.template.convertAndSend("/productcategory/snapshot",
				// new SimpleDateFormat("HH:mm:ss").format(new Date()) + "- " + message
				this.Repository.findAll());
	}



	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
	public HashMap<String, Object> Insert(@RequestBody ProductCategory item, HttpSession httpSession) {
		try {
			ProductCategory itemNew = new ProductCategory();
			long id = this.RepositoryCustom.getMaxId() + 1;
			itemNew.setId(id);
			itemNew.setCode(DateTime.getCurrentDateTimeForID() + "S" + id);
			itemNew.setAuthor(item.getAuthor());
			itemNew.setDescriptionVi(item.getDescriptionVi());
			itemNew.setDescriptionEn(item.getDescriptionEn());
			itemNew.setBigCategoryId(item.getBigCategoryId());
			itemNew.setNameEn(item.getNameEn());
			itemNew.setNameVi(item.getNameVi());
			itemNew.setStatus(item.getStatus());
			itemNew.setImgUrl(item.getImgUrl());
			itemNew.setMetaKeyword(item.getMetaKeyword());
			itemNew.setMetaTitle(item.getMetaTitle());
			itemNew.setMetaDiscription(item.getMetaDiscription());
			itemNew.setAuthor(httpSession.getAttribute("auth").toString());
			itemNew.setCreateDate(DateTime.getCurrentDateTime());
			this.Repository.insert(itemNew);

			HashMap<String, Object> map = new HashMap<>();
			map.put("result", true);
			this.template.convertAndSend("/productcategory/snapshot", this.Repository.findAll());
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
	public HashMap<String, Object> update(@RequestBody ProductCategory item, HttpSession httpSession) {

		boolean result = this.RepositoryCustom.update(item,
				httpSession.getAttribute("auth").toString());
		if (result) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("result", true);
			this.template.convertAndSend("/productcategory/snapshot", this.Repository.findAll());
			return map;
		} else {
			HashMap<String, Object> map = new HashMap<>();
			map.put("result", false);
			return map;
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> Delete(@PathVariable("id") String id) throws IOException {

		// TODO process the file here
		if (this.RepositoryCustom.delete(id)) {
			this.template.convertAndSend("/productcategory/snapshot", this.Repository.findAll());
			return ResponseEntity.ok().body("ok");
		} else {
			return ResponseEntity.badRequest().body("fail");
		}

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ProductCategory> GetByID(@PathVariable("id") String id) throws IOException {

		// TODO process the file here

		return ResponseEntity.ok().body(this.RepositoryCustom.getById(Long.parseLong(id)));

	}
	

	@ResponseBody
	@RequestMapping(value = "/by-big-category-id/{id}", method = RequestMethod.GET, produces = "application/json")

	public List<ProductCategory> showAllByBigId(@PathVariable("id") String id) {
		List<ProductCategory> item = this.Repository.findByBigCategoryId(Long.parseLong(id));
		return item;
	}
	
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")

	public List<ProductCategory> showAll() {
		List<ProductCategory> item = this.Repository.findAll();
		return item;
	}


}