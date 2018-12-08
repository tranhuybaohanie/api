package com.example.api.controllers;

//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.Authentication;
import com.example.api.configuration.CurrentUser;
import com.example.api.dao.EmployeeRepository;
import com.example.api.dao.EmployeeRepositoryCustom;
import com.example.api.dao.RoleRepository;
import com.example.api.model.BigCategory;
import com.example.api.model.Employee;
import com.example.api.model.Role;
import com.example.api.model.User;
import com.example.api.services.UserService;
import com.example.api.utilities.DateTime;
import com.fasterxml.jackson.databind.node.ObjectNode;

//@CrossOrigin(origins = {"http://localhost:4200"},
//maxAge = 4800, allowCredentials = "false") 
@Controller

public class LoginController {

	@Autowired
	UserService userService;
	@Autowired
	RoleRepository roleRepository;

	// @ResponseBody
	// @RequestMapping("home")
	// public String home() {
	// String html = "";
	// html += "<ul>";
	// html += " <li><a href='testInsert'>Test Insert</a></li>";
	// html += " <li><a href='showAllEmployee'>Show All Employee</a></li>";
	// html += " <li><a href='showFullNameLikeTom'>Show All 'Tom'</a></li>";
	// html += " <li><a href='deleteAllEmployee'>Delete All Employee</a></li>";
	// html += "</ul>";
	// return html;
	// }
	
	@ResponseBody
	@RequestMapping(value = "logout-success", method = RequestMethod.GET, produces = "application/json")
	public HashMap<String, Object> logoutsucess() {

			HashMap<String, Object> map = new HashMap<>();
			map.put("result", true);
		
			return map;
		
	}

	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
	public HashMap<String, Object> Insert(@RequestBody BigCategory bigCategoryPost) {
		try {

			Role adminRole = roleRepository.findByRole("ADMIN");
			if (adminRole == null) {
				Role newAdminRole = new Role();
				newAdminRole.setRole("ADMIN");
				roleRepository.save(newAdminRole);
			}

			Role userRole = roleRepository.findByRole("USER");
			if (userRole == null) {
				Role newUserRole = new Role();
				newUserRole.setRole("USER");
				roleRepository.save(newUserRole);
			}
			HashMap<String, Object> map = new HashMap<>();
			map.put("result", true);
			return map;
		} catch (Exception e) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("result", false);
			map.put("error", e);
			return map;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/auth", method = RequestMethod.GET, produces = "application/json")
	public String currentUserName(Authentication authentication, HttpSession httpSession) {
		try {
			// return authentication.getName();
			return httpSession.getAttribute("auth").toString();
		} catch (Exception e) {
			return "false";
		}
		// return "okokokko";
	}
	@ResponseBody
	@RequestMapping(value = "/authname", method = RequestMethod.GET, produces = "application/json")
	public HashMap<String, Object> currentUserNameau(Authentication authentication, HttpSession httpSession) {
		try {
			// return authentication.getName();
			
			HashMap<String, Object> map = new HashMap<>();
			map.put("result", true);
			map.put("fullname", userService.findUserByEmail(httpSession.getAttribute("auth").toString()).getFullname());
		
			return map;
			
		} catch (Exception e) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("result", false);
			map.put("error", e);
			return map;
		}
		// return "okokokko";
	}

	@ResponseBody
	@RequestMapping(value = "/login/{error}", method = RequestMethod.GET, produces = "application/json")
	public HashMap<String, Object> showError(@PathVariable("error") Boolean error, Authentication authentication,
			HttpSession httpSession) {
		// HashMap<String, Object>
		// String html = "";
		// for (UserStaff emp : userStaff) {
		// html += emp + "<br>";
		// }
		// Role adminRole = roleRepository.findByRole("ADMIN");
		// if (adminRole == null) {
		// Role newAdminRole = new Role();
		// newAdminRole.setRole("ADMIN");
		// roleRepository.save(newAdminRole);
		// }

		// Role userRole = roleRepository.findByRole("USER");
		// if (userRole == null) {
		// Role newUserRole = new Role();
		// newUserRole.setRole("USER");
		// roleRepository.save(newUserRole);
		// }
		HashMap<String, Object> map = new HashMap<>();
		if (error == false) {
			httpSession.setAttribute("auth", "");
			map.put("session", httpSession.getAttribute("auth"));
			map.put("result", false);
			map.put("error", error);
		} else {
			httpSession.setAttribute("auth", authentication.getName());
			map.put("session", httpSession.getAttribute("auth"));
			map.put("result", true);
			map.put("error", error);

		}

		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/signup-admin", method = RequestMethod.POST, produces = "application/json")
	public HashMap<String, Object>  sigupPost(@RequestBody User user, @RequestBody Role role) {

		User userNew = new User();

		userNew.setEmail("bao1@gmail.com");
		userNew.setEnabled(true);
		userNew.setFullname("tran huy bao");
		userNew.setId("1");
		userNew.setPassword("abc");

		userService.saveUserAdmin(userNew, role);
		 HashMap<String, Object> map = new HashMap<>();
		 map.put("result", true);
		 map.put("error", "");
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/signup", method = RequestMethod.POST, produces = "application/json")
	public  HashMap<String, Object>  sigupPostClient(@RequestBody User user) {
		if (user.getEmail() == null && user.getPassword() == null) {
			HashMap<String, Object> map = new HashMap<>();
			 map.put("result",false );
			 map.put("error", "You need input username && password");
			return map;
		} 
		if (userService.findUserByEmail(user.getEmail()) != null) {
			HashMap<String, Object> map = new HashMap<>();
			 map.put("result", false);
			 map.put("error", "This email is existed");
			return map;
		}
		User userNew = new User();
		userNew.setEmail(user.getEmail());
		userNew.setEnabled(true);
		userNew.setFullname(user.getFullname());
		userNew.setId(String.valueOf((Integer.parseInt(userService.getMaxId()) + 1)));
		userNew.setPassword(user.getPassword());
		if(userService.saveUser(userNew)) {
			
			HashMap<String, Object> map = new HashMap<>();
			 map.put("result", true);
			 map.put("user", userNew);
			 map.put("error", "");
			return map;
		}else {
			HashMap<String, Object> map = new HashMap<>();
			 map.put("result", false);
			 map.put("error", "Cannot create user");
			return map;
		}
		//
		 
	}
	
//	 @ResponseBody
//	 @RequestMapping("/showFullNameLikeTom")
//	 public String showFullNameLikeTom() {
//	
//	 List<UserStaff> userStaff =
//	 this.userStaffRepository.findByFullNameLike("Tom");
//	
//	 String html = "";
//	 for (UserStaff emp : userStaff) {
//	 html += emp + "<br>";
//	 }
//	
//	 return html;
//	 }
//	
//	 @ResponseBody
//	 @RequestMapping("/deleteAllEmployee")
//	 public String deleteAllEmployee() {
//	
//	 this.userStaffRepository.deleteAll();
//	 return "Deleted!";
//	 }

}