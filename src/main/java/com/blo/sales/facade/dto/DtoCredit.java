package com.blo.sales.facade.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.blo.sales.facade.enums.CommonStatusEnum;

import lombok.Data;

public @Data class DtoCredit implements Serializable {

	private static final long serialVersionUID = -1249071704002915037L;

	private String id;

	private String lender_name;

	private BigDecimal total_amount;

	private BigDecimal current_amount;

	private List<DtoPartialPyment> partial_payment;

	private long open_date;

	private long last_update_date;

	private CommonStatusEnum status_credit;

}
