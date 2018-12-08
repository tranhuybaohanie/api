package com.example.api.model;                                                            
                                                                                          
import java.util.List;

import org.springframework.data.annotation.Id;                                            
import org.springframework.data.mongodb.core.index.Indexed;                               
import org.springframework.data.mongodb.core.mapping.Document;                            
import org.springframework.data.mongodb.core.mapping.Field;                               
                                              
public class Order_OrderItem {                                                            
                                                                                          
         private  Order order;
         private  List<OrderItem> orderItem;
		public Order getOrder() {
			return order;
		}
		public void setOrder(Order order) {
			this.order = order;
		}
		public List<OrderItem> getOrderItem() {
			return orderItem;
		}
		public void setOrderItem(List<OrderItem> orderItem) {
			this.orderItem = orderItem;
		}
}                                                              