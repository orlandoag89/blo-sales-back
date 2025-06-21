package com.blo.sales.business.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

public @Data class DtoIntProducts implements Serializable {

	private static final long serialVersionUID = 8141669372601379558L;
	
	private List<DtoIntProduct> products;
}
