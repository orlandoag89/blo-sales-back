package com.blo.sales.facade.dto;

import java.io.Serializable;

import com.blo.sales.facade.enums.RolesEnum;

import lombok.Data;

public @Data class DtoUser implements Serializable {
	
	private static final long serialVersionUID = -3210652972409302403L;
	
	private String id;

	private String username;
	
	private String password;
	
	private String password_confirm;
	
	private RolesEnum role;

}
