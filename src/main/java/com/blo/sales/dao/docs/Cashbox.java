package com.blo.sales.dao.docs;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.Document;

import com.blo.sales.dao.enums.DocStatusCashboxEnum;

import lombok.Data;

@Document(collation = "cashboxes")
public @Data class Cashbox implements Serializable {
	
	private static final long serialVersionUID = -7291701276850331402L;

	private String id;
	
	private BigDecimal money;
	
	private long date;
	
	private DocStatusCashboxEnum status;

}
