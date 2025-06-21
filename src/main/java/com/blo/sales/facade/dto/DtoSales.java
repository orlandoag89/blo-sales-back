package com.blo.sales.facade.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

public @Data class DtoSales implements Serializable {

	private static final long serialVersionUID = -8846000645208697092L;
	
	private List<DtoSale> sales;
}
