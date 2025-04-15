package com.blo.sales.facade.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntSale;
import com.blo.sales.business.dto.DtoIntSales;
import com.blo.sales.facade.dto.DtoSale;
import com.blo.sales.facade.dto.DtoSales;
import com.blo.sales.utils.IToInner;
import com.blo.sales.utils.IToOuter;

@Component
public class DtoSalesMapper implements IToInner<DtoIntSales, DtoSales>, IToOuter<DtoIntSales, DtoSales> {
	
	@Autowired
	private DtoSaleMapper saleMapper;

	@Override
	public DtoSales toOuter(DtoIntSales inner) {
		if (inner == null) {
			return null;
		}
		
		DtoSales sales = new DtoSales();
		List<DtoSale> salesList = new ArrayList<>();
		
		if (inner.getSales() != null && !inner.getSales().isEmpty()) {
			inner.getSales().forEach(s -> salesList.add(saleMapper.toOuter(s)));
		}
		
		sales.setSales(salesList);
		return sales;
	}

	@Override
	public DtoIntSales toInner(DtoSales outer) {
		if (outer == null) {
			return null;
		}
		
		DtoIntSales salesOut = new DtoIntSales();
		List<DtoIntSale> sales = new ArrayList<>();
		
		if (outer.getSales() == null && !outer.getSales().isEmpty()) {
			outer.getSales().forEach(s -> sales.add(saleMapper.toInner(s)));
		}
		
		salesOut.setSales(sales);
		return salesOut;
	}

}
