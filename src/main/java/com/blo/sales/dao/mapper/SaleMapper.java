package com.blo.sales.dao.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntSale;
import com.blo.sales.business.dto.DtoIntSaleProduct;
import com.blo.sales.dao.docs.Sale;
import com.blo.sales.dao.docs.SaleProduct;
import com.blo.sales.utils.IToInner;
import com.blo.sales.utils.IToOuter;

@Component
public class SaleMapper implements IToInner<Sale, DtoIntSale>, IToOuter<Sale, DtoIntSale> {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private SaleProductMapper saleProductMapper;

	@Override
	public Sale toInner(DtoIntSale outer) {
		if (outer == null) {
			return null;
		}
		
		var out = modelMapper.map(outer, Sale.class);
		
		List<SaleProduct> products = new ArrayList<>();
		if (outer.getProducts() != null && !outer.getProducts().isEmpty()) {
			outer.getProducts().forEach(c -> products.add(saleProductMapper.toInner(c)));
		}
		out.setProducts(products);
		
		return out;
	}

	@Override
	public DtoIntSale toOuter(Sale inner) {
		if (inner == null) {
			return null;
		}
		
		var out = modelMapper.map(inner, DtoIntSale.class);
		
		List<DtoIntSaleProduct> products = new ArrayList<>();
		if (inner.getProducts() != null && !inner.getProducts().isEmpty()) {
			inner.getProducts().forEach(p -> products.add(saleProductMapper.toOuter(p)));
		}
		out.setProducts(products);
		
		return out;
	}
	
	
}
