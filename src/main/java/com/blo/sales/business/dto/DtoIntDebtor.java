package com.blo.sales.business.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

public @Data class DtoIntDebtor implements Serializable {
	
	private static final long serialVersionUID = 50138406652870768L;

	private String id;
	
	private String name;
	
	private BigDecimal total; //deuda que tiene
	
	private long open_date;
	
	private List<DtoIntPartialPyment> partial_pyments;
	
	private List<DtoIntSale> sales;
	
	public List<DtoIntPartialPyment> getPartial_pyments() {
		if (partial_pyments == null) {
			return new ArrayList<>();
		}
		return partial_pyments;
	}

	public List<DtoIntSale> getSales() {
		if (sales == null) {
			return new ArrayList<>();
		}
		return sales;
	}
}
