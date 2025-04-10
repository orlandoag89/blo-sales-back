package com.blo.sales.business.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

public @Data class DtoIntSales implements Serializable {

	private static final long serialVersionUID = -2253635601516700541L;

	private List<DtoIntSale> sales;
}
