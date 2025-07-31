package com.blo.sales.business.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.blo.sales.business.enums.CommonStatusIntEnum;

import lombok.Data;

public @Data class DtoIntCredit implements Serializable {

	private static final long serialVersionUID = 6834851230432246874L;

	private String id;

	private String lender_name;

	private BigDecimal total_amount;

	private BigDecimal current_amount;

	private List<DtoIntPartialPyment> partial_payment;

	private long open_date;

	private long last_update_date;

	private CommonStatusIntEnum status_credit;

}
