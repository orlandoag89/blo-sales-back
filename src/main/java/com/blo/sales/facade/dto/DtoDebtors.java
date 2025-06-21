package com.blo.sales.facade.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class DtoDebtors implements Serializable {

	private static final long serialVersionUID = -5401766240525375666L;

	private List<DtoDebtor> debtors;
}
