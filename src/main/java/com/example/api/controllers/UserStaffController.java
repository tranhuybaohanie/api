package com.example.api.controllers;


import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.api.dao.EmployeeRepository;
import com.example.api.dao.EmployeeRepositoryCustom;
import com.example.api.model.Employee;

@Controller
@RequestMapping("/api/employee")
public class UserStaffController {

   private static final String[] NAMES = { "Tom", "Jerry", "Donald" };

   @Autowired
   private EmployeeRepositoryCustom employeeRepositoryCustom;

   @Autowired
   private EmployeeRepository employeeRepository;

   @ResponseBody
   @RequestMapping("")
   public String home() {
       String html = "";
       html += "<ul>";
       html += " <li><a href='/api/employee/testInsert'>Test Insert</a></li>";
       html += " <li><a href='/api/employee/showAllEmployee'>Show All Employee</a></li>";
       html += " <li><a href='/api/employee/showFullNameLikeTom'>Show All 'Tom'</a></li>";
       html += " <li><a href='/api/employee/deleteAllEmployee'>Delete All Employee</a></li>";
       html += "</ul>";
       return html;
   }

   @ResponseBody
   @RequestMapping("testInsert")
   public String testInsert() {
       Employee employee = new Employee();

       long id = this.employeeRepositoryCustom.getMaxEmpId() + 1;
       int idx = (int) (id % NAMES.length);
       String fullName = NAMES[idx] + " " + id;

       employee.setId(id);
       employee.setEmpNo("E" + id);
       employee.setFullName(fullName);
       employee.setHireDate(new Date());
       this.employeeRepository.insert(employee);

       return "Inserted: " + employee;
   }

   @ResponseBody
   @RequestMapping("/showAllEmployee")
   public String showAllEmployee() {

       List<Employee> employees = this.employeeRepository.findAll();

       String html = "";
       for (Employee emp : employees) {
           html += emp + "<br>";
       }

       return html;
   }

   @ResponseBody
   @RequestMapping("/showFullNameLikeTom")
   public String showFullNameLikeTom() {

       List<Employee> employees = this.employeeRepository.findByFullNameLike("Tom");

       String html = "";
       for (Employee emp : employees) {
           html += emp + "<br>";
       }

       return html;
   }

   @ResponseBody
   @RequestMapping("/deleteAllEmployee")
   public String deleteAllEmployee() {

       this.employeeRepository.deleteAll();
       return "Deleted!";
   }

}