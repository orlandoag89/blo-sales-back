package com.blo.sales.dao.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntCredit;
import com.blo.sales.business.dto.DtoIntCredits;
import com.blo.sales.dao.docs.Credits;
import com.blo.sales.utils.IToOuter;

@Component
public class CreditsMapper implements IToOuter<Credits, DtoIntCredits> {
	
	@Autowired
	private CreditMaper mapper;

	@Override
	public DtoIntCredits toOuter(Credits inner) {
		if (inner == null) {
			return null;
		}
		var out = new DtoIntCredits();
		List<DtoIntCredit> credits = new ArrayList<>();
		
		if (inner.getCredits() != null && !inner.getCredits().isEmpty()) {
			inner.getCredits().forEach(c -> credits.add(mapper.toOuter(c)));
		}
		
		out.setCredits(credits);
		return out;
	}

}
