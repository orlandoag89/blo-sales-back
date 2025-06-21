package com.blo.sales.facade.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntCashboxes;
import com.blo.sales.facade.dto.DtoCashbox;
import com.blo.sales.facade.dto.DtoCashboxes;
import com.blo.sales.utils.IToOuter;

@Component
public class DtoCashboxesMapper implements IToOuter<DtoIntCashboxes, DtoCashboxes> {
	
	@Autowired
	private DtoCashboxMapper cashboxMapper;	

	@Override
	public DtoCashboxes toOuter(DtoIntCashboxes inner) {
		if (inner == null) {
			return null;
		}
		DtoCashboxes out = new DtoCashboxes();
		List<DtoCashbox> boxes = new ArrayList<>();
		
		if (inner.getBoxes() != null && !inner.getBoxes().isEmpty()) {
			inner.getBoxes().forEach(b -> boxes.add(cashboxMapper.toOuter(b)));
		}
		
		out.setBoxes(boxes);
		return out;
		
	}

}
