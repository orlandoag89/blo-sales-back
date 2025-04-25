package com.blo.sales.dao.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntPartialPyment;
import com.blo.sales.dao.docs.PartialPyment;
import com.blo.sales.utils.IToInner;
import com.blo.sales.utils.IToOuter;

@Component
public class PartialPymentMapper implements IToInner<PartialPyment, DtoIntPartialPyment>, IToOuter<PartialPyment, DtoIntPartialPyment> {

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PartialPyment toInner(DtoIntPartialPyment outer) {
		if (outer == null) {
			return null;
		}
		return modelMapper.map(outer, PartialPyment.class);
	}

	@Override
	public DtoIntPartialPyment toOuter(PartialPyment inner) {
		if (inner == null) {
			return null;
		}
		return modelMapper.map(inner, DtoIntPartialPyment.class);
	}
}
