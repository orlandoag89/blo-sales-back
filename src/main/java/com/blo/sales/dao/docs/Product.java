package com.blo.sales.dao.docs;

import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "products")
public @Data class Product {
	
	private String id;

	private String name;
	
	private BigDecimal total_price;

	private int quantity;

	private String desc;
	
	private boolean its_kg;
}
