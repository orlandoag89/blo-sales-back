package com.blo.sales.business.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

public @Data class DtoIntDebtor {
	
	private String id;
	
	private String name;
	
	private BigDecimal total; //deuda que tiene
	
	private int open_date;
	
	private List<BigDecimal> partial_pyments;
	
	private List<String> sales_id;
	
	private List<DtoIntProduct> products;
	
	public List<DtoIntProduct> getProducts() {
		if (products == null) {
			return new ArrayList<>();
		}
		return this.products;
	}
	
	public List<BigDecimal> getPartial_pyments() {
		if (partial_pyments == null) {
			return new ArrayList<>();
		}
		return partial_pyments;
	}

}
