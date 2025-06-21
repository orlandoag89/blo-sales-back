package com.blo.sales.dao.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntSaleProduct;
import com.blo.sales.dao.docs.SaleProduct;
import com.blo.sales.utils.IToInner;
import com.blo.sales.utils.IToOuter;

@Component
public class SaleProductMapper implements IToInner<SaleProduct, DtoIntSaleProduct>, IToOuter<SaleProduct, DtoIntSaleProduct> {
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public SaleProduct toInner(DtoIntSaleProduct outer) {
		if (outer == null) {
			return null;
		}
		return modelMapper.map(outer, SaleProduct.class);
	}

	@Override
	public DtoIntSaleProduct toOuter(SaleProduct inner) {
		if (inner == null) {
			return null;
		}
		return modelMapper.map(inner, DtoIntSaleProduct.class);
	}

}
