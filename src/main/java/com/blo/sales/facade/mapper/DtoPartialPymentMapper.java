package com.blo.sales.facade.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntPartialPyment;
import com.blo.sales.facade.dto.DtoPartialPyment;
import com.blo.sales.utils.IToInner;
import com.blo.sales.utils.IToOuter;

@Component
public class DtoPartialPymentMapper implements IToInner<DtoIntPartialPyment, DtoPartialPyment>,
		IToOuter<DtoIntPartialPyment, DtoPartialPyment> {
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public DtoIntPartialPyment toInner(DtoPartialPyment outer) {
		if (outer == null) {
			return null;
		}
		return modelMapper.map(outer, DtoIntPartialPyment.class); 
	}

	@Override
	public DtoPartialPyment toOuter(DtoIntPartialPyment inner) {
		if (inner == null) {
			return null;
		}
		return modelMapper.map(inner, DtoPartialPyment.class);
	}

}
