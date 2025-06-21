package com.blo.sales.facade.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DtoProducts implements Serializable {

	private static final long serialVersionUID = -2325167465564600383L;
	
	private List<DtoProduct> products;
}
