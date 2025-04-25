package com.blo.sales.dao.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blo.sales.factory.MocksFactory;

@SpringBootTest
public class SaleMapperTest {
	
	@Autowired
	private SaleMapper mapper;
	
	@Test
	private void toInnerTest() {
		var outer = MocksFactory.createDtoIntSaleNoCashbox();
		
		var inner = mapper.toInner(outer);
		
		assertNotNull(inner);
		assertEquals(outer.getId(), inner.getId());
		assertFalse(inner.getProducts().isEmpty());
	}
	
	@Test
	public void toInnerNullTest() {
		assertNull(mapper.toInner(null));
	}
	
	@Test
	public void toInnerProductsNullTest() {
		var outer = MocksFactory.createDtoIntSaleNoCashbox();
		outer.setProducts(null);
		
		var inner = mapper.toInner(outer);
		
		assertNotNull(inner);
		assertTrue(inner.getProducts().isEmpty());
	}
	
	@Test
	public void toInnerProductsEmptyTest() {
		var outer = MocksFactory.createDtoIntSaleNoCashbox();
		outer.getProducts().clear();
		
		var inner = mapper.toInner(outer);
		
		assertNotNull(inner);
		assertTrue(inner.getProducts().isEmpty());
	}
	
	@Test
	public void toOuterTest() {
		var inner = MocksFactory.createSaleNoCashbox();
		
		var outer = mapper.toOuter(inner);
		
		assertNotNull(outer);
		assertEquals(inner.getId(), outer.getId());
		assertFalse(outer.getProducts().isEmpty());
	}
	
	@Test
	public void toOuterNullTest() {
		assertNull(mapper.toOuter(null));
	}

	@Test
	public void toOuterProductsNullTest() {
		var inner = MocksFactory.createSaleNoCashbox();
		inner.setProducts(null);
		
		var outer = mapper.toOuter(inner);
		
		assertNotNull(outer);
		assertEquals(inner.getId(), outer.getId());
		assertTrue(outer.getProducts().isEmpty());
	}
	
	@Test
	public void toOuterProductsEmptyTest() {
		var inner = MocksFactory.createSaleNoCashbox();
		inner.getProducts().clear();
		
		var outer = mapper.toOuter(inner);
		
		assertNotNull(outer);
		assertEquals(inner.getId(), outer.getId());
		assertTrue(outer.getProducts().isEmpty());
	}
}
