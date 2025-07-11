package com.blo.sales.dao.docs;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

public @Data class Providers implements Serializable {

	private static final long serialVersionUID = 1870451271356835970L;
	
	private List<Provider> providers;
}
