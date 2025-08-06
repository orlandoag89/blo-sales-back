package com.blo.sales.dao.docs;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.blo.sales.dao.enums.DocCommonStatusEnum;

import lombok.Data;

@Document(collection = "credits")
public @Data class Credit implements Serializable {

	private static final long serialVersionUID = -4030654099401501332L;

	private String id;

	private String lender_name;

	private BigDecimal total_amount;

	private BigDecimal current_amount;

	private List<PartialPyment> partial_payment;

	private long open_date;

	private long last_update_date;

	private DocCommonStatusEnum status_credit;

}
