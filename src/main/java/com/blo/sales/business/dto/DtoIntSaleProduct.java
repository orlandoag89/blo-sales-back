package com.blo.sales.business.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

public class DtoIntSaleProduct extends DtoIntProduct implements Serializable {

	private static final long serialVersionUID = -4644728045416095561L;
	
	@Setter @Getter
	private BigDecimal quantity_on_sale;
	
}
