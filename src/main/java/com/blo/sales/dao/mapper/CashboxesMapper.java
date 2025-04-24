package com.blo.sales.dao.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntCashbox;
import com.blo.sales.business.dto.DtoIntCashboxes;
import com.blo.sales.dao.docs.Cashbox;
import com.blo.sales.dao.docs.Cashboxes;
import com.blo.sales.utils.IToInner;
import com.blo.sales.utils.IToOuter;

@Component
public class CashboxesMapper implements IToInner<Cashboxes, DtoIntCashboxes>, IToOuter<Cashboxes, DtoIntCashboxes> {
	
	@Autowired
	private CashboxMapper mapper;

	@Override
	public DtoIntCashboxes toOuter(Cashboxes inner) {
		if (inner == null) {
			return null;
		}
		var out = new DtoIntCashboxes();
		List<DtoIntCashbox> boxes = new ArrayList<>();
		
		if (inner.getBoxes() != null && !inner.getBoxes().isEmpty()) {
			inner.getBoxes().forEach(b -> boxes.add(mapper.toOuter(b)));
		}
		
		out.setBoxes(boxes);
		return out;
	}

	@Override
	public Cashboxes toInner(DtoIntCashboxes outer) {
		if (outer == null) {
			return null;
		}
		
		var out = new Cashboxes();
		List<Cashbox> boxes = new ArrayList<>();
		
		if (outer.getBoxes() != null && !outer.getBoxes().isEmpty()) {
			outer.getBoxes().forEach(b -> boxes.add(mapper.toInner(b)));
		}
		
		out.setBoxes(boxes);
		return out;
	}

}
