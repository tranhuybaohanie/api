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

import com.example.api.dao.BigProductCategoryRepository;
import com.example.api.dao.BigProductCategoryRepositoryCustom;
import com.example.api.dao.EmployeeRepository;
import com.example.api.dao.EmployeeRepositoryCustom;
import com.example.api.model.BigCategory;
import com.example.api.model.Employee;
import com.example.api.model.UploadFileResponse;
import com.example.api.utilities.DateTime;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Controller

@RequestMapping("/big-category")
public class BigCategoryController {

	private static final String[] NAMES = { "Tom", "Jerry", "Donald" };

	@Autowired
	private BigProductCategoryRepositoryCustom bigCategoryRepositoryCustom;

	@Autowired
	private BigProductCategoryRepository bigCategoryRepository;

	@Autowired
	private final SimpMessagingTemplate template;

	@Autowired
	BigCategoryController(SimpMessagingTemplate template) {
		this.template = template;
	}

	@MessageMapping("/update/bigcategory")
	public void onReceivedMesage(String message) {
		System.out.println("send: " + message);
		this.template.convertAndSend("/bigcategory/snapshot",
				// new SimpleDateFormat("HH:mm:ss").format(new Date()) + "- " + message
				this.bigCategoryRepository.findAll());
	}

	@ResponseBody
	@RequestMapping("home")
	public String home() {
		String html = "";
		html += "<ul>";
		html += " <li><a href='testInsert'>Test Insert</a></li>";
		html += " <li><a href='showAllEmployee'>Show All Employee</a></li>";
		html += " <li><a href='showFullNameLikeTom'>Show All 'Tom'</a></li>";
		html += " <li><a href='deleteAllEmployee'>Delete All Employee</a></li>";
		html += "</ul>";
		return html;
	}

	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
	public HashMap<String, Object> Insert(@RequestBody BigCategory bigCategoryPost, HttpSession httpSession) {
		try {
			BigCategory bigCategory = new BigCategory();
			long id = this.bigCategoryRepositoryCustom.getMaxId() + 1;
			bigCategory.setId(id);
			bigCategory.setCode(DateTime.getCurrentDateTimeForID() + "S" + id);
			bigCategory.setAuthor(bigCategoryPost.getAuthor());
			bigCategory.setDescriptionVi(bigCategoryPost.getDescriptionVi());
			bigCategory.setDescriptionEn(bigCategoryPost.getDescriptionEn());
			bigCategory.setNameEn(bigCategoryPost.getNameEn());
			bigCategory.setNameVi(bigCategoryPost.getNameVi());
			bigCategory.setStatus(bigCategoryPost.getStatus());
			bigCategory.setImgUrl(bigCategoryPost.getImgUrl());
			bigCategory.setMetaKeyword(bigCategoryPost.getMetaKeyword());
			bigCategory.setMetaTitle(bigCategoryPost.getMetaTitle());
			bigCategory.setMetaDiscription(bigCategoryPost.getMetaDiscription());
			bigCategory.setAuthor(httpSession.getAttribute("auth").toString());
			bigCategory.setCreateDate(DateTime.getCurrentDateTime());
			this.bigCategoryRepository.insert(bigCategory);

			HashMap<String, Object> map = new HashMap<>();
			map.put("result", true);
			this.template.convertAndSend("/bigcategory/snapshot", this.bigCategoryRepository.findAll());
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
	public HashMap<String, Object> update(@RequestBody BigCategory bigCategoryPost, HttpSession httpSession) {

		boolean result = this.bigCategoryRepositoryCustom.update(bigCategoryPost,
				httpSession.getAttribute("auth").toString());
		if (result) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("result", true);
			this.template.convertAndSend("/bigcategory/snapshot", this.bigCategoryRepository.findAll());
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
		if (this.bigCategoryRepositoryCustom.delete(id)) {
			this.template.convertAndSend("/bigcategory/snapshot", this.bigCategoryRepository.findAll());
			return ResponseEntity.ok().body("ok");
		} else {
			return ResponseEntity.badRequest().body("fail");
		}

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<BigCategory> GetByID(@PathVariable("id") String id) throws IOException {

		// TODO process the file here

		return ResponseEntity.ok().body(this.bigCategoryRepositoryCustom.getById(Long.parseLong(id)));

	}

	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")

	public List<BigCategory> showAll() {
		List<BigCategory> bigCategory = this.bigCategoryRepository.findAll();

		// String html = "";
		// for (UserStaff emp : userStaff) {
		// html += emp + "<br>";
		// }

		return bigCategory;
	}

	// @ResponseBody
	// @RequestMapping("/showFullNameLikeTom")
	// public String showFullNameLikeTom() {
	//
	// List<UserStaff> userStaff =
	// this.userStaffRepository.findByFullNameLike("Tom");
	//
	// String html = "";
	// for (UserStaff emp : userStaff) {
	// html += emp + "<br>";
	// }
	//
	// return html;
	// }
	//
	// @ResponseBody
	// @RequestMapping("/deleteAllEmployee")
	// public String deleteAllEmployee() {
	//
	// this.userStaffRepository.deleteAll();
	// return "Deleted!";
	// }

}