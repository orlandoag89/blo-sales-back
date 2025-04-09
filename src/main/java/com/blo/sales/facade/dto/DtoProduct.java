package com.blo.sales.facade.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

public @Data class DtoProduct implements Serializable {

	private static final long serialVersionUID = -8003497902290230115L;

	private String id;

    private String name;

    private BigDecimal total_price;

    private BigDecimal quantity;

    private String desc;

    private  boolean its_kg;
}
