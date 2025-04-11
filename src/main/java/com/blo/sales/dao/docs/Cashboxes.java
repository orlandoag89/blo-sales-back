package com.blo.sales.dao.docs;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

public @Data class Cashboxes implements Serializable {

	private static final long serialVersionUID = 6135745968607877748L;
	
	private List<Cashbox> boxes;
}
