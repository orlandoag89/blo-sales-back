package com.blo.sales.dao.docs;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collation = "sales")
public @Data class Sale implements Serializable {
	
	private static final long serialVersionUID = -3210349383143698308L;

	private String id;
	
	private long open_date; //fecha que se realizó
	
	private BigDecimal total; //total de la venta
	
	private long close_sale; //fecha en que se terminó la venta
	
	private boolean is_on_cashbox;//ya está en la caja
	
	private List<SaleProduct> products; //productos de la venta

}
