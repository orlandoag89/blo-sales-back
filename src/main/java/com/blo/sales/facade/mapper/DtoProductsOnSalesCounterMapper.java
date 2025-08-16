package com.blo.sales.facade.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntProductsOnSalesCounter;
import com.blo.sales.facade.dto.DtoProductOnSaleCounter;
import com.blo.sales.facade.dto.DtoProductsOnSalesCounter;
import com.blo.sales.utils.IToOuter;

@Component
public class DtoProductsOnSalesCounterMapper
		implements IToOuter<DtoIntProductsOnSalesCounter, DtoProductsOnSalesCounter> {

	@Autowired
	private DtoProductOnSaleCounterMapper mapper;
	
	@Override
	public DtoProductsOnSalesCounter toOuter(DtoIntProductsOnSalesCounter inner) {
		if (inner == null) {
			return null;
		}
		var out = new DtoProductsOnSalesCounter();
		List<DtoProductOnSaleCounter> productsOnSales = new ArrayList<>();
		
		if (inner.getProductsOnSales() != null && !inner.getProductsOnSales().isEmpty()) {
			inner.getProductsOnSales().forEach(c -> productsOnSales.add(mapper.toOuter(c)));
		}
		
		out.setProductsOnSales(productsOnSales);
		return out;
		
	}

}
