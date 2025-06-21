package com.blo.sales.facade.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntProduct;
import com.blo.sales.facade.dto.DtoProduct;
import com.blo.sales.utils.IToInner;
import com.blo.sales.utils.IToOuter;

@Component
public class DtoProductMapper implements IToInner<DtoIntProduct, DtoProduct>, IToOuter<DtoIntProduct, DtoProduct> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public DtoIntProduct toInner(DtoProduct outer) {
		if (outer == null) {
			return null;
		}
		
		return modelMapper.map(outer, DtoIntProduct.class); 
	}

	@Override
	public DtoProduct toOuter(DtoIntProduct inner) {
		if (inner == null) {
			return null;
		}
		return modelMapper.map(inner, DtoProduct.class);
	}

}
