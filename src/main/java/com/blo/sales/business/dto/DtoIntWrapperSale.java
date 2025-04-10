package com.blo.sales.business.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

public @Data class DtoIntWrapperSale implements Serializable {
	
	private static final long serialVersionUID = 7279794246892688635L;

	private DtoIntSale sale;
	
	private DtoIntDebtor detor;
	
	private List<DtoIntProduct> productsWithAlerts;

}
