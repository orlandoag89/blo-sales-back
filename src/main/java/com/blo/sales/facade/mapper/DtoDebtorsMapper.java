package com.blo.sales.facade.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntDebtor;
import com.blo.sales.business.dto.DtoIntDebtors;
import com.blo.sales.facade.dto.DtoDebtor;
import com.blo.sales.facade.dto.DtoDebtors;
import com.blo.sales.utils.IToInner;
import com.blo.sales.utils.IToOuter;

@Component
public class DtoDebtorsMapper implements IToInner<DtoIntDebtors, DtoDebtors>, IToOuter<DtoIntDebtors, DtoDebtors> {
	
	@Autowired
	private DtoDebtorMapper mapper;

	@Override
	public DtoDebtors toOuter(DtoIntDebtors inner) {
		if (inner == null) {
			return null;
		}
		var out = new DtoDebtors();
		List<DtoDebtor> debtors = new ArrayList<>();
		
		if (inner.getDebtors() != null && !inner.getDebtors().isEmpty()) {
			inner.getDebtors().forEach(d -> debtors.add(mapper.toOuter(d)));
		}
		
		out.setDebtors(debtors);
		return out;
	}

	@Override
	public DtoIntDebtors toInner(DtoDebtors outer) {
		if (outer == null) {
			return null;
		}
		var out = new DtoIntDebtors();
		List<DtoIntDebtor> debtors = new ArrayList<>();
		
		if (outer.getDebtors() != null && !outer.getDebtors().isEmpty()) {
			outer.getDebtors().forEach(d -> debtors.add(mapper.toInner(d)));
		}
		
		out.setDebtors(debtors);
		return out;
	}

}
