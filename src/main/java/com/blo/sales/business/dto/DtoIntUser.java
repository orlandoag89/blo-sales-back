package com.blo.sales.business.dto;

import java.io.Serializable;

import com.blo.sales.business.enums.RolesIntEnum;

import lombok.Data;

public @Data class DtoIntUser implements Serializable {
	
	private static final long serialVersionUID = -1611114580309479122L;
	
	private String id;

	private String username;

	private String password;
	
	private RolesIntEnum role;
}
