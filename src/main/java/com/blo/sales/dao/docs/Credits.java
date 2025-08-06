package com.blo.sales.dao.docs;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

public @Data class Credits implements Serializable {

	private static final long serialVersionUID = -7184212391789078345L;
	
	private List<Credit> credits;
}
