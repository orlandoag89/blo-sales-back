package com.blo.sales.facade.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

public class DtoSaleProduct extends DtoProduct implements Serializable {

	private static final long serialVersionUID = -1882360788586933451L;
	
	@Setter @Getter
	private BigDecimal quantity_on_sale;
}
