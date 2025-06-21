package com.blo.sales.business.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

public @Data class DtoIntDebtors implements Serializable {
	
	private static final long serialVersionUID = 6188622495404202227L;
	
	private List<DtoIntDebtor> debtors;

}
