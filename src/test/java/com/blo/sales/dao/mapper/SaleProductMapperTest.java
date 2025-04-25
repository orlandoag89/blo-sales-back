package com.blo.sales.dao.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blo.sales.factory.MocksFactory;

@SpringBootTest
public class SaleProductMapperTest {
	
	@Autowired
	private SaleProductMapper mapper;
	
	@Test
	public void toInnerTest() {
		var outer = MocksFactory.createDtoIntSaleProduct();
		
		var inner = mapper.toInner(outer);
		
		assertNotNull(inner);
		assertEquals(outer.getId(), inner.getId());
	}

	@Test
	public void toInnerNullTest() {
		var inner = mapper.toInner(null);
		
		assertNull(inner);
	}
	
	@Test
	public void toOuterTest() {
		var inner = MocksFactory.createSaleProduct();
		
		var outer = mapper.toOuter(inner);
		
		assertNotNull(outer);
		assertEquals(inner.getId(), outer.getId());
	}
	
	@Test
	public void toOuterNullTest() {
		var outer = mapper.toOuter(null);
		
		assertNull(outer);
	}
}
