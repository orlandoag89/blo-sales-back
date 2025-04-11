package com.blo.sales.facade.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.blo.sales.business.enums.StatusCashboxIntEnum;

import lombok.Data;

public @Data class DtoCashbox implements Serializable {

	private static final long serialVersionUID = -3359942797516228197L;

	private String id;

	private BigDecimal money;

	private long date;

	private StatusCashboxIntEnum status;
}
