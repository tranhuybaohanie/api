package com.example.api.model;                                                            
                                                                                          
import org.springframework.data.annotation.Id;                                            
import org.springframework.data.mongodb.core.index.Indexed;                               
import org.springframework.data.mongodb.core.mapping.Document;                            
import org.springframework.data.mongodb.core.mapping.Field;                               
@Document(collection = "Order")                                                
public class Order {                                                            
                                                                                          
	@Id                                                                                   
	private long id;                                                                      
                                                                                          
	@Indexed(unique = true)                                                               
	@Field(value = "code")                                                                
	private String code;     
	                                                   
                                                                                          
	@Field(value = "create_date")                                                         
	private String createDate;    
	
	@Field(value = "fullname")                                                         
	private String fullName;    
	
	public String getFullName() {
		return fullName;
	}



	public void setFullName(String fullName) {
		this.fullName = fullName;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}

	@Field(value = "phone")                                                         
	private String phone;    
	
	@Field(value = "address")                                                         
	private String address;    
	
	@Field(value = "user")                                                         
	private String user;    
                                                        
	public String getUser() {
		return user;
	}



	public void setUser(String user) {
		this.user = user;
	}

	@Field(value = "status")                                                              
	private Boolean status;   
	

	@Field(value = "received_state")                                                              
	private Boolean receivedState;   
	@Field(value = "delivering_state")                                                              
	private Boolean deliveringState;   
	@Field(value = "delivered_state")                                                              
	private Boolean deliveredState;   
	
                              
	
	public void setId(long id) {
		this.id = id;
	}
                              
                    
                                                                                          
	public Long getId() {                                                                 
		return id;                                                                        
	}                                                                                     
                                                                                          
	public void setId(Long id) {                                                          
		this.id = id;                                                                     
	}                                                                                     
                                                                                          
	public String getCode() {                                                             
		return code;                                                                      
	}                                                                                     
                                                                                          
	public void setCode(String code) {                                                    
		this.code = code;                                                                 
	}                                                                                     
                                                                                          
	public String getCreateDate() {                                                       
		return createDate;                                                                
	}                                                                                     
                                                                                          
	public void setCreateDate(String createDate) {                                        
		this.createDate = createDate;                                                     
	}                                                                                     
                                                                                          
	                                                
	public Boolean getReceivedState() {
		return receivedState;
	}

	public void setReceivedState(Boolean receivedState) {
		this.receivedState = receivedState;
	}

	public Boolean getDeliveringState() {
		return deliveringState;
	}

	public void setDeliveringState(Boolean deliveringState) {
		this.deliveringState = deliveringState;
	}

	public Boolean getDeliveredState() {
		return deliveredState;
	}

	public void setDeliveredState(Boolean deliveredState) {
		this.deliveredState = deliveredState;
	}

	public Boolean getStatus() {                                                          
		return status;                                                                    
	}                                                                                     
                                                                                          
	public void setStatus(Boolean status) {                                               
		this.status = status;                                                             
	}                                                                                     
                                                                                          
                                                                                          
}                                                                                         