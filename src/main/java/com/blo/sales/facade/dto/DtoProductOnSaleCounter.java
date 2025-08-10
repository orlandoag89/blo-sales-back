package com.blo.sales.facade.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

public @Data class DtoProductOnSaleCounter implements Serializable {
	
	private static final long serialVersionUID = 8568495758529882841L;

	private String name;

	private BigDecimal total_sold;

	private BigDecimal total_revenue;

	private int time_sold;
}
