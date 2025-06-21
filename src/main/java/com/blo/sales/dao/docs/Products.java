package com.blo.sales.dao.docs;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

public @Data class Products implements Serializable {
	
	private static final long serialVersionUID = 1493492770268882929L;
	
	private List<Product> products;

}
