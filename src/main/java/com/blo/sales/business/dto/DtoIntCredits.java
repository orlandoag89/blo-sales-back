package com.blo.sales.business.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

public @Data class DtoIntCredits implements Serializable {
	
	private static final long serialVersionUID = 8097854189666540713L;
	
	private List<DtoIntCredit> credits;

}
