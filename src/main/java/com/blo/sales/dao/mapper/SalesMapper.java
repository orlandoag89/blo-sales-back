package com.blo.sales.dao.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntSale;
import com.blo.sales.business.dto.DtoIntSales;
import com.blo.sales.dao.docs.Sale;
import com.blo.sales.dao.docs.Sales;
import com.blo.sales.utils.IToInner;
import com.blo.sales.utils.IToOuter;

@Component
public class SalesMapper implements IToInner<Sales, DtoIntSales>, IToOuter<Sales, DtoIntSales> {

	@Autowired
	private SaleMapper saleMapper;
	
	@Override
	public Sales toInner(DtoIntSales outer) {
		if (outer == null) {
			return null;
		}
		var output = new Sales();
		
		List<Sale> sales = new ArrayList<>();
		if (outer.getSales() != null && !outer.getSales().isEmpty()) {
			outer.getSales().forEach(s -> sales.add(saleMapper.toInner(s)));
		}
		output.setSales(sales);
		
		return output;
	}

	@Override
	public DtoIntSales toOuter(Sales inner) {
		if (inner == null) {
			return null;
		}
		var output = new DtoIntSales();
		
		List<DtoIntSale> sales = new ArrayList<>();
		if (inner.getSales() != null && !inner.getSales().isEmpty()) {
			inner.getSales().forEach(s -> sales.add(saleMapper.toOuter(s)));
		}
		output.setSales(sales);
		
		return output;
	}

}
