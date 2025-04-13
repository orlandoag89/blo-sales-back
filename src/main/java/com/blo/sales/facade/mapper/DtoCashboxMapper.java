package com.blo.sales.facade.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntCashbox;
import com.blo.sales.facade.dto.DtoCashbox;
import com.blo.sales.utils.IToInner;
import com.blo.sales.utils.IToOuter;

@Component
public class DtoCashboxMapper implements IToInner<DtoIntCashbox, DtoCashbox>, IToOuter<DtoIntCashbox, DtoCashbox> {
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public DtoIntCashbox toInner(DtoCashbox outer) {
		if (outer == null) {
			return null;
		}
		return mapper.map(outer, DtoIntCashbox.class);
	}

	@Override
	public DtoCashbox toOuter(DtoIntCashbox inner) {
		if (inner == null) {
			return null;
		}
		return mapper.map(inner, DtoCashbox.class);
	}
}
