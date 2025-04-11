package com.blo.sales.business.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

public @Data class DtoIntCashboxes implements Serializable {

	private static final long serialVersionUID = -8007708595801580798L;
	
	private List<DtoIntCashbox> boxes;

}
