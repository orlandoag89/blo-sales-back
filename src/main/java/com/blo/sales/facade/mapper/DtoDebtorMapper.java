package com.blo.sales.facade.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntDebtor;
import com.blo.sales.business.dto.DtoIntSale;
import com.blo.sales.facade.dto.DtoDebtor;
import com.blo.sales.facade.dto.DtoSale;
import com.blo.sales.utils.IToInner;
import com.blo.sales.utils.IToOuter;

@Component
public class DtoDebtorMapper implements IToInner<DtoIntDebtor, DtoDebtor>, IToOuter<DtoIntDebtor, DtoDebtor> {
	
	@Autowired
	private ModelMapper modelapper;
	
	@Autowired
	private DtoSaleMapper saleMapper;

	@Override
	public DtoIntDebtor toInner(DtoDebtor outer) {
		if (outer == null ) {
			return null;
		}
		
		var debtor = modelapper.map(outer, DtoIntDebtor.class);
		List<DtoIntSale> sales = new ArrayList<>();
		
		if (outer.getSales() != null && !outer.getSales().isEmpty()) {
			outer.getSales().forEach(s -> sales.add(saleMapper.toInner(s)));
		}
		
		debtor.setSales(sales);
		return debtor;
	}

	@Override
	public DtoDebtor toOuter(DtoIntDebtor inner) {
		if (inner == null) {
			return null;
		}
		var debtor = modelapper.map(inner, DtoDebtor.class);
		List<DtoSale> sales = new ArrayList<>();
		
		if (inner.getSales() != null && !inner.getSales().isEmpty()) {
			inner.getSales().forEach(s -> sales.add(saleMapper.toOuter(s)));
		}
		
		debtor.setSales(sales);
		return debtor;
	}

}
