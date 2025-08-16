package com.blo.sales.facade.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

public @Data class DtoSaleDetailReport implements Serializable {

	private static final long serialVersionUID = 1675559232454083784L;

	private int year;

	private int month;

	private BigDecimal totalVentas;

}
