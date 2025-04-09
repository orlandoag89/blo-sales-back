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
	
	private List<BigDecimal> partial_pyments;
	
	private List<String> sales_id;
	
	public List<BigDecimal> getPartial_pyments() {
		if (partial_pyments == null) {
			return new ArrayList<>();
		}
		return partial_pyments;
	}

}
