package com.blo.sales.factory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.blo.sales.business.dto.DtoIntProduct;
import com.blo.sales.business.dto.DtoIntProducts;
import com.blo.sales.facade.dto.DtoProduct;
import com.blo.sales.facade.dto.DtoProducts;

public final class MocksFactory {
	
	private static final String ANY_STRING = "any_string";
	private static final String ANY_ID = "1a2b3c4d5e";
	private static final BigDecimal BIG_DECIMAL_5 = new BigDecimal("5");
	private static final BigDecimal BIG_DECIMAL_50 = new BigDecimal("50");
	
	private MocksFactory() { }
	
	public static DtoProduct createDtoProduct() {
		var out = new DtoProduct();
		out.setId(ANY_ID);
		out.setDesc(ANY_STRING);
		out.setIts_kg(false);
		out.setName(ANY_STRING);
		out.setQuantity(BIG_DECIMAL_5);
		out.setTotal_price(BIG_DECIMAL_50);
		return out;
	}
	
	public static DtoIntProduct createDtoIntProduct() {
		var out = new DtoIntProduct();
		out.setId(ANY_ID);
		out.setDesc(ANY_STRING);
		out.setIts_kg(false);
		out.setName(ANY_STRING);
		out.setQuantity(BIG_DECIMAL_5);
		out.setTotal_price(BIG_DECIMAL_50);
		return out;
	}
	
	public static DtoProducts createDtoProducts() {
		var out = new DtoProducts();
		List<DtoProduct> products = new ArrayList<>();
		products.add(createDtoProduct());
		out.setProducts(products);
		return out;
	}
	
	public static DtoProducts createDtoProductsEmptyList() {
		var out = new DtoProducts();
		List<DtoProduct> products = new ArrayList<>();
		out.setProducts(products);
		return out;
	}
	
	public static DtoProducts createDtoProductsNullList() {
		var out = new DtoProducts();
		out.setProducts(null);
		return out;
	}
	
	public static DtoIntProducts createDtoIntProducts() {
		var out = new DtoIntProducts();
		List<DtoIntProduct> products = new ArrayList<>();
		products.add(createDtoIntProduct());
		out.setProducts(products);
		return out;
	}
	
	public static DtoIntProducts createDtoIntProductsEmptyList() {
		var out = new DtoIntProducts();
		List<DtoIntProduct> products = new ArrayList<>();
		out.setProducts(products);
		return out;
	}
	
	public static DtoIntProducts createDtoIntProductsNullList() {
		var out = new DtoIntProducts();
		out.setProducts(null);
		return out;
	}
}
