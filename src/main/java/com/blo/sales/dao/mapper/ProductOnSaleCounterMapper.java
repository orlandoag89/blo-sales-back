package com.blo.sales.dao.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntProductOnSaleCounter;
import com.blo.sales.dao.docs.ProductsOnSaleCounter;
import com.blo.sales.utils.IToOuter;

@Component
public class ProductOnSaleCounterMapper implements IToOuter<ProductsOnSaleCounter, DtoIntProductOnSaleCounter> {
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public DtoIntProductOnSaleCounter toOuter(ProductsOnSaleCounter inner) {
		if (inner == null) {
			return null;
		}
		return modelMapper.map(inner, DtoIntProductOnSaleCounter.class);
	}

}
