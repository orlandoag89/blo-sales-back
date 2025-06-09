package com.blo.sales.dao.docs;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "configs")
public @Data class Config implements Serializable {
	
	private static final long serialVersionUID = -3406015015888176840L;

	private String id;
	
	private boolean ciphered;

}
