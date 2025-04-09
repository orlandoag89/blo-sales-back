package com.blo.sales.dao.docs;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "products")
public @Data class Product implements Serializable {
	
	private static final long serialVersionUID = -8821952502682383680L;

	private String id;

	private String name;
	
	private BigDecimal total_price;

	private BigDecimal quantity;

	private String desc;
	
	private boolean its_kg;
}
