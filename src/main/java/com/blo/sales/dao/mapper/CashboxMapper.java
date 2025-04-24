package com.blo.sales.dao.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntCashbox;
import com.blo.sales.business.enums.StatusCashboxIntEnum;
import com.blo.sales.dao.docs.Cashbox;
import com.blo.sales.dao.enums.DocStatusCashboxEnum;
import com.blo.sales.utils.IToInner;
import com.blo.sales.utils.IToOuter;

@Component
public class CashboxMapper implements IToInner<Cashbox, DtoIntCashbox>, IToOuter<Cashbox, DtoIntCashbox> {

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public DtoIntCashbox toOuter(Cashbox inner) {
		if (inner == null) {
			return null;
		}
		var out = modelMapper.map(inner, DtoIntCashbox.class);
		out.setStatus(StatusCashboxIntEnum.valueOf(inner.getStatus().name()));
		return out;
	}

	@Override
	public Cashbox toInner(DtoIntCashbox outer) {
		if (outer == null) {
			return null;
		}
		var out = modelMapper.map(outer, Cashbox.class);
		out.setStatus(DocStatusCashboxEnum.valueOf(outer.getStatus().name()));
		return out;
	}
	
}
