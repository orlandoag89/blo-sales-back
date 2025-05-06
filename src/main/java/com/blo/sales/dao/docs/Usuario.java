package com.blo.sales.dao.docs;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.blo.sales.dao.enums.DocRolesEnum;

import lombok.Data;

@Document(collection = "usuarios")
public @Data class Usuario implements Serializable {
	
	private static final long serialVersionUID = -9131993483920383817L;

	private String id;

	private String username;

	private List<String> password;
	
	private DocRolesEnum rol;

}
