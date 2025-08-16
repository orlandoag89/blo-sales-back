package com.blo.sales.dao.docs;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

public @Data class SaleDetailReport implements Serializable {

	private static final long serialVersionUID = -5623310544522568452L;
	
	private int year;
	
	private int month;
	
	private BigDecimal totalVentas;
}
