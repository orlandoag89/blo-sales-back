package com.blo.sales.facade.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntSaleProduct;
import com.blo.sales.facade.dto.DtoSaleProduct;
import com.blo.sales.utils.IToInner;
import com.blo.sales.utils.IToOuter;

@Component
public class DtoSaleProductMapper
		implements IToInner<DtoIntSaleProduct, DtoSaleProduct>, IToOuter<DtoIntSaleProduct, DtoSaleProduct> {
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public DtoSaleProduct toOuter(DtoIntSaleProduct inner) {
		if (inner == null) {
			return null;
		}
		return mapper.map(inner, DtoSaleProduct.class); 
	}

	@Override
	public DtoIntSaleProduct toInner(DtoSaleProduct outer) {
		if (outer == null) {
			return null;
		}
		return mapper.map(outer, DtoIntSaleProduct.class);
	}

}
