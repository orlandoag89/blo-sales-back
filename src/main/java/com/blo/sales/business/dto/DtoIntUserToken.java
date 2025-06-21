package com.blo.sales.business.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class DtoIntUserToken implements Serializable {

	private static final long serialVersionUID = 6841040071928768216L;

	@Getter
	private String token;
}
