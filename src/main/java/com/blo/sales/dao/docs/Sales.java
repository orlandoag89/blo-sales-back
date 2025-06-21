package com.blo.sales.dao.docs;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Sales implements Serializable {

	private static final long serialVersionUID = 4065712755701521467L;
	
	private List<Sale> sales;

}
