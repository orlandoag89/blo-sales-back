package com.blo.sales.business.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

public @Data class DtoIntProvider implements Serializable {

	private static final long serialVersionUID = -7162806401693064512L;

	private String name;
	
	private List<String> products_offered;
	
	private String phoneNumber;
	
	private List<String> days_visit;
}
