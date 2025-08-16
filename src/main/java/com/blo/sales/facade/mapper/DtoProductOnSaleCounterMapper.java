package com.blo.sales.facade.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntProductOnSaleCounter;
import com.blo.sales.facade.dto.DtoProductOnSaleCounter;
import com.blo.sales.utils.IToOuter;

@Component
public class DtoProductOnSaleCounterMapper implements IToOuter<DtoIntProductOnSaleCounter, DtoProductOnSaleCounter> {
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public DtoProductOnSaleCounter toOuter(DtoIntProductOnSaleCounter inner) {
		if (inner == null) {
			return null;
		}
		return mapper.map(inner, DtoProductOnSaleCounter.class);
	}

}
