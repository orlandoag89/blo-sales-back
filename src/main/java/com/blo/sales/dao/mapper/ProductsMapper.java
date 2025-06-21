package com.blo.sales.dao.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntProduct;
import com.blo.sales.business.dto.DtoIntProducts;
import com.blo.sales.dao.docs.Product;
import com.blo.sales.dao.docs.Products;
import com.blo.sales.utils.IToInner;
import com.blo.sales.utils.IToOuter;

@Component
public class ProductsMapper implements IToInner<Products, DtoIntProducts>, IToOuter<Products, DtoIntProducts> {
	
	@Autowired
	private ProductMapper productMapper;

	@Override
	public Products toInner(DtoIntProducts outer) {
		if (outer == null) {
			return null;
		}
		
		var out = new Products();
		List<Product> products = new ArrayList<>();
		if (outer.getProducts() != null && !outer.getProducts().isEmpty()) {
			outer.getProducts().forEach(p -> products.add(productMapper.toInner(p)));
		}
		out.setProducts(products);
		return out;
	}

	@Override
	public DtoIntProducts toOuter(Products inner) {
		if (inner == null) {
			return null;
		}
		var out = new DtoIntProducts();
		List<DtoIntProduct> products = new ArrayList<>();
		if (inner.getProducts() != null && !inner.getProducts().isEmpty()) {
			inner.getProducts().forEach(p -> products.add(productMapper.toOuter(p)));
		}
		out.setProducts(products);
		return out;
	}

}
