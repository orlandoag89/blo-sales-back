package com.blo.sales.facade.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntCredits;
import com.blo.sales.facade.dto.DtoCredit;
import com.blo.sales.facade.dto.DtoCredits;
import com.blo.sales.utils.IToOuter;

@Component
public class DtoCreditsMapper implements IToOuter<DtoIntCredits, DtoCredits> {
	
	@Autowired
	private DtoCreditMapper mapper;

	@Override
	public DtoCredits toOuter(DtoIntCredits inner) {
		if (inner == null) {
			return null;
		}
		var out = new DtoCredits();
		List<DtoCredit> lst = new ArrayList<>();
		
		if (inner.getCredits() != null && !inner.getCredits().isEmpty()) {
			inner.getCredits().forEach(c -> lst.add(mapper.toOuter(c)));
		}
		
		out.setCredits(lst);
		return out;
	}

}
