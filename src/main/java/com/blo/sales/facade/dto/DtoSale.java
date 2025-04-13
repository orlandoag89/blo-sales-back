package com.blo.sales.facade.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

public @Data class DtoSale implements Serializable {

	private static final long serialVersionUID = 4161356848637171494L;

	private String id;

	private long open_date; // fecha que se realizó

	private BigDecimal total; // total de la venta

	private long close_sale; // fecha en que se terminó la venta

	private boolean is_on_cashbox;// ya está en la caja

	private List<DtoSaleProduct> products; // productos de la venta
}
