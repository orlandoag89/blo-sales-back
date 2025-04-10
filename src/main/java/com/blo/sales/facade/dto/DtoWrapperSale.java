package com.blo.sales.facade.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

public @Data class DtoWrapperSale implements Serializable {

	private static final long serialVersionUID = -4223031466066098369L;

	private DtoSale sale;
	
	private DtoDebtor detor;
	
	private List<DtoProduct> productsWithAlerts;
}
