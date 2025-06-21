package com.blo.sales.dao.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntProduct;
import com.blo.sales.dao.docs.Product;
import com.blo.sales.utils.IToInner;
import com.blo.sales.utils.IToOuter;

@Component
public class ProductMapper implements IToInner<Product, DtoIntProduct>, IToOuter<Product, DtoIntProduct> {
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public DtoIntProduct toOuter(Product inner) {
		if (inner == null) {
			return null;
		}
		return modelMapper.map(inner, DtoIntProduct.class);
	}

	@Override
	public Product toInner(DtoIntProduct outer) {
		if (outer == null) {
			return null;
		}
		return modelMapper.map(outer, Product.class);
	}

}
