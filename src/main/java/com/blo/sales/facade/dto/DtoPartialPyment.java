package com.blo.sales.facade.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

public @Data class DtoPartialPyment implements Serializable {

	private static final long serialVersionUID = -7759817881083124284L;
	
	private BigDecimal partial_pyment;
	
	private long date;
	
}
