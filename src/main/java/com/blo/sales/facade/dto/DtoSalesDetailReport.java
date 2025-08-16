package com.blo.sales.facade.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

public @Data class DtoSalesDetailReport implements Serializable {
	
	private static final long serialVersionUID = 6384407812088261688L;
	
	private List<DtoSaleDetailReport> sales;

}
