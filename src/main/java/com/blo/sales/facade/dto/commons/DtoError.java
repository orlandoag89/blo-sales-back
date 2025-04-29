package com.blo.sales.facade.dto.commons;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Getter class DtoError implements Serializable {
	
	private static final long serialVersionUID = -4770934792344908014L;
	
	private String code;
	
	private String message;
}
