package com.blo.sales.facade.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

public @Data class DtoProductsOnSalesCounter implements Serializable {
	
	private static final long serialVersionUID = -7559680391901434686L;
	
	private List<DtoProductOnSaleCounter> productsOnSales;
}
