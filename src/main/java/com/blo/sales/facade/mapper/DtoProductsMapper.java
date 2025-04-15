package com.blo.sales.facade.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntProduct;
import com.blo.sales.business.dto.DtoIntProducts;
import com.blo.sales.facade.dto.DtoProduct;
import com.blo.sales.facade.dto.DtoProducts;
import com.blo.sales.utils.IToInner;
import com.blo.sales.utils.IToOuter;

@Component
public class DtoProductsMapper implements IToInner<DtoIntProducts, DtoProducts>, IToOuter<DtoIntProducts, DtoProducts> {
	
	@Autowired
	private DtoProductMapper modelMapper;

	@Override
	public DtoProducts toOuter(DtoIntProducts inner) {
		if (inner == null) {
			return null;
		}
		DtoProducts out = new DtoProducts();
		List<DtoProduct> products = new ArrayList<>();
		
		if (out.getProducts() != null && !out.getProducts().isEmpty()) {
			inner.getProducts().forEach(p -> products.add(modelMapper.toOuter(p)));
		}
		
		out.setProducts(products);
		return out;
	}

	@Override
	public DtoIntProducts toInner(DtoProducts outer) {
		if (outer == null) {
			return null;
		}
		DtoIntProducts out = new DtoIntProducts();
		List<DtoIntProduct> products = new ArrayList<>();
		
		if (outer.getProducts() != null && !outer.getProducts().isEmpty()) {
			outer.getProducts().forEach(p -> products.add(modelMapper.toInner(p)));
		}
		
		out.setProducts(products);
		return out;
	}

}
