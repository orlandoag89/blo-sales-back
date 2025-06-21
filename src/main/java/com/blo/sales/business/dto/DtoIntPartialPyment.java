package com.blo.sales.business.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

public @Data class DtoIntPartialPyment implements Serializable {
	
	private static final long serialVersionUID = 5275576539237489195L;

	private BigDecimal partial_pyment;
	
	private long date;

}
