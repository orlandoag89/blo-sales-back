package com.blo.sales.business.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

public @Data class DtoIntProviders implements Serializable {
	
	private static final long serialVersionUID = 8082000426426739880L;
	
	private List<DtoIntProvider> provider;

}
