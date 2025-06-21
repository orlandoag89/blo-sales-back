package com.blo.sales.dao.docs;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

public @Data class PartialPyment implements Serializable {
	
	private static final long serialVersionUID = -8337381950065797817L;

	private BigDecimal partial_pyment;
	
	private long date;

}
