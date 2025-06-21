package com.blo.sales.dao.docs;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

public @Data class Debtors implements Serializable {
	
	private static final long serialVersionUID = 3673033231524654496L;
	
	private List<Debtor> debtors;

}
