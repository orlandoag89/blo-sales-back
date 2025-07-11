package com.blo.sales.business.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

public @Data class DtoIntProduct implements Serializable {

	private static final long serialVersionUID = 7860947563040481493L;

	private String id;

    private String name;

    private BigDecimal total_price;

    private BigDecimal cost_of_sale;
    
    private BigDecimal quantity;

    private String desc;

    private  boolean its_kg;
}
