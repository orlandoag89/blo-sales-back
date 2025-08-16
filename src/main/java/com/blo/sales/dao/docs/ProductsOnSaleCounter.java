package com.blo.sales.dao.docs;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

import lombok.Data;

public @Data class ProductsOnSaleCounter implements Serializable {

	private static final long serialVersionUID = -2870922801009882843L;

	@Id
	private String name;

	private BigDecimal total_sold;

	private BigDecimal total_revenue;

	private int time_sold;

}
