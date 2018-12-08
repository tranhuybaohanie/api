package com.example.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
 
@Document(collection = "user-staff")
public class UserStaff {
 
    @Id
    private Long id;
 
    @Indexed(unique = true)
    @Field(value = "code")
    private String userStaffNo;
 
    @Field(value = "full_name")
    private String fullName;
 
    @Field(value = "create_date")
    private String createDate;
    
    @Field(value = "address")
    private String adress ;
    
    @Field(value = "phone")
    private String phone ;
    
    @Field(value = "user_name")
    private String userName;
    
    @Field(value = "password")
    private String password;
    
    @Field(value = "email")
    private String email;
    
    @Field(value = "position")
    private String position;
    
    @Field(value = "status")
    private boolean status;
    
    public boolean isStatus() {
		return status;
	}



	public void setStatus(boolean status) {
		this.status = status;
	}



	public String getPosition() {
		return position;
	}



	public void setPosition(String position) {
		this.position = position;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}





	public String getUserStaffNo() {
		return userStaffNo;
	}



	public void setUserStaffNo(String userStaffNo) {
		this.userStaffNo = userStaffNo;
	}



	public String getFullName() {
		return fullName;
	}



	public void setFullName(String fullName) {
		this.fullName = fullName;
	}



	public String getCreateDate() {
		return createDate;
	}



	public void setCreateDate(String string) {
		this.createDate = string;
	}



	public String getAdress() {
		return adress;
	}



	public void setAdress(String adress) {
		this.adress = adress;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getImgUrl() {
		return imgUrl;
	}



	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}



	@Field(value = "img_url")
    private String imgUrl;  
  
  
   
    @Override
    public String toString() {
        return "id:" + this.id + ", empNo: " + this.userStaffNo //
                + ", fullName: " + this.fullName + ", hireDate: " + this.createDate;
    }
}