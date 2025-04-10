package com.blo.sales.business.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

public @Data class DtoIntSale implements Serializable {

	private static final long serialVersionUID = -8534148441841164081L;

	private String id;

	private long open_date; // fecha que se realizó

	private BigDecimal total; // total de la venta

	private long close_sale; // fecha en que se terminó la venta

	private boolean is_on_cashbox;// ya está en la caja

	private List<DtoIntSaleProduct> products; // productos de la venta
}
