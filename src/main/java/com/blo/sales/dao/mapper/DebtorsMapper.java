package com.blo.sales.dao.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntDebtor;
import com.blo.sales.business.dto.DtoIntDebtors;
import com.blo.sales.dao.docs.Debtors;
import com.blo.sales.utils.IToOuter;

@Component
public class DebtorsMapper implements IToOuter<Debtors, DtoIntDebtors> {
	
	@Autowired
	private DebtorMapper mapper;

	@Override
	public DtoIntDebtors toOuter(Debtors inner) {
		if (inner == null) {
			return null;
		}
		
		var out = new DtoIntDebtors();
		List<DtoIntDebtor> debtors = new ArrayList<>();
		if (inner.getDebtors() != null && !inner.getDebtors().isEmpty()) {
			inner.getDebtors().forEach(d -> debtors.add(mapper.toOuter(d)));
		}
		out.setDebtors(debtors);
		return out;
	}

}
