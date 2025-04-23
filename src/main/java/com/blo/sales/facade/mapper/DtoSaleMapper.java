package com.blo.sales.facade.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntSale;
import com.blo.sales.business.dto.DtoIntSaleProduct;
import com.blo.sales.facade.dto.DtoSale;
import com.blo.sales.facade.dto.DtoSaleProduct;
import com.blo.sales.utils.IToInner;
import com.blo.sales.utils.IToOuter;

@Component
public class DtoSaleMapper implements IToInner<DtoIntSale, DtoSale>, IToOuter<DtoIntSale, DtoSale> {
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private DtoSaleProductMapper saleProductMapper;

	@Override
	public DtoIntSale toInner(DtoSale outer) {
		if (outer == null) {
			return null;
		}
		var out = mapper.map(outer, DtoIntSale.class);
		List<DtoIntSaleProduct> products = new ArrayList<>();
		
		if (outer.getProducts() != null && !outer.getProducts().isEmpty()) {
			outer.getProducts().forEach(c -> products.add(saleProductMapper.toInner(c)));
		}
		
		out.setProducts(products);
		return out;
	}

	@Override
	public DtoSale toOuter(DtoIntSale inner) {
		if (inner == null) {
			return null;
		}
		var out = mapper.map(inner, DtoSale.class);
		List<DtoSaleProduct> products = new ArrayList<>();
		
		if (inner.getProducts() != null && !inner.getProducts().isEmpty()) {
			inner.getProducts().forEach(c -> products.add(saleProductMapper.toOuter(c)));
		}
		
		out.setProducts(products);
		return out;
	}

}
