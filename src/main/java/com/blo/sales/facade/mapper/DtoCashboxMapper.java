package com.blo.sales.facade.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntCashbox;
import com.blo.sales.business.enums.StatusCashboxIntEnum;
import com.blo.sales.facade.dto.DtoCashbox;
import com.blo.sales.facade.enums.StatusCashboxEnum;
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
		
		var output = mapper.map(outer, DtoIntCashbox.class);
		output.setStatus(StatusCashboxIntEnum.valueOf(outer.getStatus().name()));
		return output;
	}

	@Override
	public DtoCashbox toOuter(DtoIntCashbox inner) {
		if (inner == null) {
			return null;
		}
		var output = mapper.map(inner, DtoCashbox.class);
		output.setStatus(StatusCashboxEnum.valueOf(inner.getStatus().name()));
		return output;
	}
}
