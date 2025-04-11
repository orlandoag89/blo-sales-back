package com.blo.sales.facade.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

public @Data class DtoCashboxes implements Serializable {

	private static final long serialVersionUID = -8240859989315630405L;
	
	private List<DtoCashbox> boxes;

}
