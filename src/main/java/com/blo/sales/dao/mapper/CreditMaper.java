package com.blo.sales.dao.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntCredit;
import com.blo.sales.business.enums.CommonStatusIntEnum;
import com.blo.sales.dao.docs.Credit;
import com.blo.sales.dao.enums.DocCommonStatusEnum;
import com.blo.sales.utils.IToInner;
import com.blo.sales.utils.IToOuter;

@Component
public class CreditMaper implements IToInner<Credit, DtoIntCredit>, IToOuter<Credit, DtoIntCredit> {

	@Autowired
	private ModelMapper mapper;
	
	@Override
	public Credit toInner(DtoIntCredit outer) {
		if (outer == null) {
			return null;
		}
		var out = mapper.map(outer, Credit.class);
		out.setStatus_credit(DocCommonStatusEnum.valueOf(outer.getStatus_credit().name()));
		return out;
	}

	@Override
	public DtoIntCredit toOuter(Credit inner) {
		if (inner == null) {
			return null;
		}
		var out = mapper.map(inner, DtoIntCredit.class);
		out.setStatus_credit(CommonStatusIntEnum.valueOf(inner.getStatus_credit().name()));
		return out;
	}

}
