package com.blo.sales.dao.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntDebtor;
import com.blo.sales.business.dto.DtoIntPartialPyment;
import com.blo.sales.business.dto.DtoIntSale;
import com.blo.sales.dao.docs.Debtor;
import com.blo.sales.dao.docs.PartialPyment;
import com.blo.sales.dao.docs.Sale;
import com.blo.sales.utils.IToInner;
import com.blo.sales.utils.IToOuter;

@Component
public class DebtorMapper implements IToInner<Debtor, DtoIntDebtor>, IToOuter<Debtor, DtoIntDebtor> {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PartialPymentMapper partialPymentMapper;
	
	@Autowired
	private SaleMapper saleMapper;
	
	@Override
	public Debtor toInner(DtoIntDebtor outer) {
		if (outer == null) {
			return null;
		}
		
		var out = modelMapper.map(outer, Debtor.class);
		
		List<PartialPyment> partial_pyments = new ArrayList<>();
		if (outer.getPartial_pyments() != null && !outer.getPartial_pyments().isEmpty()) {
			outer.getPartial_pyments().forEach(p -> partial_pyments.add(partialPymentMapper.toInner(p)));
		}
		out.setPartial_pyments(partial_pyments);
		
		List<Sale> sales_id = new ArrayList<>();
		if (outer.getSales() != null && !outer.getSales().isEmpty()) {
			outer.getSales().forEach(s -> sales_id.add(saleMapper.toInner(s)));
		}
		out.setSales(sales_id);
		
		return out;
		
	}

	@Override
	public DtoIntDebtor toOuter(Debtor inner) {
		if (inner == null) {
			return null;
		}
		
		var out = modelMapper.map(inner, DtoIntDebtor.class);
		
		List<DtoIntPartialPyment> partial_pyments = new ArrayList<>();
		if (inner.getPartial_pyments() != null && !inner.getPartial_pyments().isEmpty()) {
			inner.getPartial_pyments().forEach(p -> partial_pyments.add(partialPymentMapper.toOuter(p)));
		}
		out.setPartial_pyments(partial_pyments);
		
		List<DtoIntSale> sales = new ArrayList<>();
		if (inner.getSales() != null && !inner.getSales().isEmpty()) {
			inner.getSales().forEach(s -> sales.add(saleMapper.toOuter(s)));
		}
		out.setSales(sales);
		
		return out;
	}

}
