package com.blo.sales.business.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

public @Data class DtoIntProductsOnSalesCounter implements Serializable {

	private static final long serialVersionUID = -9154547474488796277L;
	
	private List<DtoIntProductOnSaleCounter> productsOnSales;
}
