package com.blo.sales.facade.mapper;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blo.sales.factory.MocksFactory;

@SpringBootTest
public class DtoProductsOnSalesCounterMapperTest {
	
	@Autowired
	private DtoProductsOnSalesCounterMapper mapper;

	@Test
	public void toOuterTest() {
		var inner = MocksFactory.createDtoIntProductsOnSalesCounter();
		
		var outer = mapper.toOuter(inner);
		
		assertNotNull(outer);
		assertFalse(outer.getProductsOnSales().isEmpty());
	}
	
	@Test
	public void toOuterNullTest() {
		var outer = mapper.toOuter(null);
		
		assertNull(outer);
	}
	
	@Test
	public void toOuterNullListTest() {
		var inner = MocksFactory.createDtoIntProductsOnSalesCounter();
		inner.setProductsOnSales(null);
		
		var outer = mapper.toOuter(inner);
		
		assertNotNull(outer);
		assertTrue(outer.getProductsOnSales().isEmpty());
	}
	
	@Test
	public void toOuterEmptyListTest() {
		var inner = MocksFactory.createDtoIntProductsOnSalesCounter();
		inner.getProductsOnSales().clear();
		
		var outer = mapper.toOuter(inner);
		
		assertNotNull(outer);
		assertTrue(outer.getProductsOnSales().isEmpty());
	}
}
