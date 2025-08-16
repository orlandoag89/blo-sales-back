package com.blo.sales.business.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

public @Data class DtoIntSaleDetailReport implements Serializable {

	private static final long serialVersionUID = 6949425732455146662L;

	private int year;

	private int month;

	private BigDecimal totalVentas;

}
