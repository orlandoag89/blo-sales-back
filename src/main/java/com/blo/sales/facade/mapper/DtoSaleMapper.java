package com.blo.sales.facade.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntSale;
import com.blo.sales.facade.dto.DtoSale;
import com.blo.sales.utils.IToInner;
import com.blo.sales.utils.IToOuter;

@Component
public class DtoSaleMapper implements IToInner<DtoIntSale, DtoSale>, IToOuter<DtoIntSale, DtoSale> {
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public DtoIntSale toInner(DtoSale outer) {
		if (outer == null) {
			return null;
		}
		return mapper.map(outer, DtoIntSale.class);
	}

	@Override
	public DtoSale toOuter(DtoIntSale inner) {
		if (inner == null) {
			return null;
		}
		return mapper.map(inner, DtoSale.class);
	}

}
