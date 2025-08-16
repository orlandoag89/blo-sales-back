package com.blo.sales.business.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

public @Data class DtoIntProductOnSaleCounter implements Serializable {

	private static final long serialVersionUID = -3688017925534393938L;
	
	private String name;

	private BigDecimal total_sold;

	private BigDecimal total_revenue;

	private int time_sold;
}
