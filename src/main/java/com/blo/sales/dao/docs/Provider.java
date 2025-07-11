package com.blo.sales.dao.docs;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "providers")
public @Data class Provider implements Serializable {
	
	private static final long serialVersionUID = -6724997663493775081L;

	private String name;
	
	private List<String> products_offered;
	
	private String phoneNumber;

	private List<String> days_visit;
}
