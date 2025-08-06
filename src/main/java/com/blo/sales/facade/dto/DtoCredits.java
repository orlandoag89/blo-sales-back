package com.blo.sales.facade.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

public @Data class DtoCredits implements Serializable {
	
	private static final long serialVersionUID = -2135047848327562258L;
	
	private List<DtoCredit> credits;

}
