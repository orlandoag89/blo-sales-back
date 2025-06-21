package com.blo.sales.business.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.blo.sales.business.enums.StatusCashboxIntEnum;

import lombok.Data;

public @Data class DtoIntCashbox implements Serializable {

	private static final long serialVersionUID = -2193815082162608774L;

	private String id;

	private BigDecimal money;

	private long date;

	private StatusCashboxIntEnum status;

}
