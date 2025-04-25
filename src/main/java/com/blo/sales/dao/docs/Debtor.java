package com.blo.sales.dao.docs;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collation = "debtors")
public @Data class Debtor implements Serializable {

	private static final long serialVersionUID = -4973741841840522936L;

	private String id;

	private String name;

	private BigDecimal total; // deuda que tiene

	private long open_date;

	private List<PartialPyment> partial_pyments;

	private List<Sale> sales;

}
