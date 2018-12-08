package com.example.api.model;                                                            
                                                                                          
import org.springframework.data.annotation.Id;                                            
import org.springframework.data.mongodb.core.index.Indexed;                               
import org.springframework.data.mongodb.core.mapping.Document;                            
import org.springframework.data.mongodb.core.mapping.Field;                               
@Document(collection = "product")                                                
public class Product {                                                            
                                                                                          
	@Id                                                                                   
	private long id;                                                                      
                                                                                          
	@Indexed(unique = true)                                                               
	@Field(value = "code")                                                                
	private String code;                                                                  
	@Field(value = "description_en")                                                      
	private String descriptionEn;                                                         
                                                                                          
	@Field(value = "big_category_id")                                                     
	private long bigCategoryId;    
	
	@Field(value = "product_category_id")                                                     
	private long productCategoryId;   
	
	@Field(value = "price")                                                     
	private int price;  
	
	@Field(value = "view_count")                                                     
	private int viewCount;  
	
	
	public long getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(long productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public int getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(int promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getIngredient() {
		return ingredient;
	}

	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}

	public String getNutritionalValue() {
		return nutritionalValue;
	}

	public void setNutritionalValue(String nutritionalValue) {
		this.nutritionalValue = nutritionalValue;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setBigCategoryId(long bigCategoryId) {
		this.bigCategoryId = bigCategoryId;
	}

	@Field(value = "promotion_price")                                                     
	private int promotionPrice;   
	
	@Field(value = "currency")                                                     
	private String currency;   
	
	@Field(value = "ingredient")                                                    
	private String ingredient;   
	
	@Field(value = "nutritional_value ")                                                     
	private String nutritionalValue ; 
	
	
	
	public long getBigCategoryId() {                                                      
		return bigCategoryId;                                                             
	}                                                                                     
                                                                                          
	public void setBigCategoryId(Long bigCategoryId) {                                    
		this.bigCategoryId = bigCategoryId;                                               
	}                                                                                     
                                                                                          
	@Field(value = "description_vi")                                                      
	private String descriptionVi;                                                         
                                                                                          
	@Field(value = "create_date")                                                         
	private String createDate;                                                            
                                                                                          
	@Field(value = "author")                                                              
	private String author;                                                                
                                                                                          
	@Field(value = "name_en")                                                             
	private String nameEn;                                                                
                                                                                          
	@Field(value = "name_vi")                                                             
	private String nameVi;                                                                
                                                                                          
	public String getModifiedBy() {                                                       
		return modifiedBy;                                                                
	}                                                                                     
                                                                                          
	public void setModifiedBy(String modifiedBy) {                                        
		this.modifiedBy = modifiedBy;                                                     
	}                                                                                     
                                                                                          
	public String getModifiedDate() {                                                     
		return modifiedDate;                                                              
	}                                                                                     
                                                                                          
	public void setModifiedDate(String modifiedDate) {                                    
		this.modifiedDate = modifiedDate;                                                 
	}                                                                                     
                                                                                          
	@Field(value = "img_url")                                                             
	private String imgUrl;  
	
	@Field(value = "img_more_url")                                                             
	private String imgMoreUrl;      
                                                                                          
	public String getImgMoreUrl() {
		return imgMoreUrl;
	}

	public void setImgMoreUrl(String imgMoreUrl) {
		this.imgMoreUrl = imgMoreUrl;
	}

	@Field(value = "modified_by")                                                         
	private String modifiedBy;                                                            
                                                                                          
	@Field(value = "modified_date")                                                       
	private String modifiedDate;                                                          
                                                                                          
	@Field(value = "meta_title")                                                          
	private String metaTitle;                                                             
                                                                                          
	public String getImgUrl() {                                                           
		return imgUrl;                                                                    
	}                                                                                     
                                                                                          
	public void setImgUrl(String imgUrl) {                                                
		this.imgUrl = imgUrl;                                                             
	}                                                                                     
                                                                                          
	public String getMetaTitle() {                                                        
		return metaTitle;                                                                 
	}                                                                                     
                                                                                          
	public void setMetaTitle(String metaTitle) {                                          
		this.metaTitle = metaTitle;                                                       
	}                                                                                     
                                                                                          
	public String getMetaKeyword() {                                                      
		return metaKeyword;                                                               
	}                                                                                     
                                                                                          
	public void setMetaKeyword(String metaKeyword) {                                      
		this.metaKeyword = metaKeyword;                                                   
	}                                                                                     
                                                                                          
	public String getMetaDiscription() {                                                  
		return metaDiscription;                                                           
	}                                                                                     
                                                                                          
	public void setMetaDiscription(String metaDiscription) {                              
		this.metaDiscription = metaDiscription;                                           
	}                                                                                     
                                                                                          
	@Field(value = "meta_keyword")                                                        
	private String metaKeyword;                                                           
                                                                                          
	@Field(value = "meta_discription")                                                    
	private String metaDiscription;                                                       
                                                                                          
	@Field(value = "status")                                                              
	private Boolean status;                                                               
                                                                                          
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
                                                                                          
	public String getDescriptionEn() {                                                    
		return descriptionEn;                                                             
	}                                                                                     
                                                                                          
	public void setDescriptionEn(String descriptionEn) {                                  
		this.descriptionEn = descriptionEn;                                               
	}                                                                                     
                                                                                          
	public String getDescriptionVi() {                                                    
		return descriptionVi;                                                             
	}                                                                                     
                                                                                          
	public void setDescriptionVi(String descriptionVi) {                                  
		this.descriptionVi = descriptionVi;                                               
	}                                                                                     
                                                                                          
	public String getNameEn() {                                                           
		return nameEn;                                                                    
	}                                                                                     
                                                                                          
	public void setNameEn(String nameEn) {                                                
		this.nameEn = nameEn;                                                             
	}                                                                                     
                                                                                          
	public String getNameVi() {                                                           
		return nameVi;                                                                    
	}                                                                                     
                                                                                          
	public void setNameVi(String nameVi) {                                                
		this.nameVi = nameVi;                                                             
	}                                                                                     
                                                                                          
	public String getAuthor() {                                                           
		return author;                                                                    
	}                                                                                     
                                                                                          
	public void setAuthor(String author) {                                                
		this.author = author;                                                             
	}                                                                                     
                                                                                          
	public Boolean getStatus() {                                                          
		return status;                                                                    
	}                                                                                     
                                                                                          
	public void setStatus(Boolean status) {                                               
		this.status = status;                                                             
	}                                                                                     
                                                                                          
                                                                                          
}                                                                                         