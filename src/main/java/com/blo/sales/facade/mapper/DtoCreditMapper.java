package com.blo.sales.facade.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntCredit;
import com.blo.sales.business.enums.CommonStatusIntEnum;
import com.blo.sales.facade.dto.DtoCredit;
import com.blo.sales.facade.enums.CommonStatusEnum;
import com.blo.sales.utils.IToInner;
import com.blo.sales.utils.IToOuter;

@Component
public class DtoCreditMapper implements IToInner<DtoIntCredit, DtoCredit>, IToOuter<DtoIntCredit, DtoCredit> {
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public DtoCredit toOuter(DtoIntCredit inner) {
		if (inner == null) {
			return null;
		}
		var out = modelMapper.map(inner, DtoCredit.class);
		out.setStatus_credit(CommonStatusEnum.valueOf(inner.getStatus_credit().name()));
		return out;
	}

	@Override
	public DtoIntCredit toInner(DtoCredit outer) {
		if (outer == null) {
			return null;
		}
		var inner = modelMapper.map(outer, DtoIntCredit.class);
		inner.setStatus_credit(CommonStatusIntEnum.valueOf(outer.getStatus_credit().name()));
		return inner;
	}

}
