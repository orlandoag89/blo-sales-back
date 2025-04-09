package com.blo.sales.dao.docs;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.blo.sales.business.dto.DtoIntProduct;

import lombok.Data;

@Document(collation = "debtors")
public @Data class Debtor {

	private String id;

	private String name;

	private BigDecimal total; // deuda que tiene

	private int open_date;

	private List<Integer> partial_pyments;

	private List<String> sales_id;

	private List<DtoIntProduct> products;

}
