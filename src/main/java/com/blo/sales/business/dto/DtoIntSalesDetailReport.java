package com.blo.sales.business.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

public @Data class DtoIntSalesDetailReport implements Serializable {
	
	private static final long serialVersionUID = -4528052615750304353L;
	
	private List<DtoIntSaleDetailReport> sales;

}
