package com.blo.sales.facade.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;  // Para Java EE 8 / Spring Boot 2.x
import javax.validation.constraints.Pattern;

import java.io.Serializable;
import java.math.BigDecimal;

public @Data class DtoProduct implements Serializable {

	private static final long serialVersionUID = -8003497902290230115L;

	private String id;

	@NotNull(message = "El nombre del producto es necesario")
    private String name; // nombre del producto

	@NotNull(message = "El precio del producto es necesario")
	private BigDecimal total_price; // precio del producto

	@NotNull(message = "La cantidad del producto en existencia es necesaria")
	@Pattern(regexp = "^[0-9]+")
    private BigDecimal quantity; // cantidad en existencia

	@NotNull(message = "Breve descripción del producto")
    private String desc; //breve descripción

	@NotNull(message = "Bandera obligatoria")
    private  boolean its_kg; //son kg
}
