package com.blo.sales.facade.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

public @Data class DtoDebtor implements Serializable {

	private static final long serialVersionUID = 1611258371830409896L;

	private String id;

	private String name;

	private BigDecimal total; // deuda que tiene

	private long open_date;

	private List<DtoPartialPyment> partial_pyments;

	private List<DtoSale> sales;
	
	public List<DtoPartialPyment> getPartial_pyments() {
		if (partial_pyments == null) {
			return new ArrayList<>();
		}
		return partial_pyments;
	}
}
