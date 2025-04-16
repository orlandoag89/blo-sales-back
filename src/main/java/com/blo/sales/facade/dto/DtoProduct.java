package com.blo.sales.facade.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

public @Data class DtoProduct implements Serializable {

	private static final long serialVersionUID = -8003497902290230115L;

	private String id;

    private String name; // nombre del producto

    private BigDecimal total_price; // precio del producto

    private BigDecimal quantity; // cantidad en existencia

    private String desc; //breve descripción

    private  boolean its_kg; //son kg
}
