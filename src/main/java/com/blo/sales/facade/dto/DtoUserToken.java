package com.blo.sales.facade.dto;

import java.io.Serializable;

import lombok.Data;

public @Data class DtoUserToken implements Serializable {

	private static final long serialVersionUID = 2639058407028013281L;
	
	private String token;
}
