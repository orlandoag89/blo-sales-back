package com.blo.sales.dao.mapper;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blo.sales.factory.MocksFactory;

@SpringBootTest
public class SalesMapperTest {
	
	@Autowired
	private SalesMapper mapper;

	@Test
	public void toInnerTest() {
		var inner = mapper.toInner(MocksFactory.createDtoIntSales());
		
		assertNotNull(inner);
		assertFalse(inner.getSales().isEmpty());
	}
	
	@Test
	public void toInnerNullTest() {
		assertNull(mapper.toInner(null));
	}
	
	@Test
	public void toInnerNullListTest() {
		var outer = MocksFactory.createDtoIntSales();
		outer.setSales(null);
		
		var inner = mapper.toInner(outer);
		
		assertNotNull(inner);
		assertTrue(inner.getSales().isEmpty());
	}
	
	@Test
	public void toInnerEmptyListTest() {
		var outer = MocksFactory.createDtoIntSales();
		outer.getSales().clear();
		
		var inner = mapper.toInner(outer);
		
		assertNotNull(inner);
		assertTrue(inner.getSales().isEmpty());
	}
	
	@Test
	public void toOuterTest() {
		var inner = MocksFactory.createSales();
		
		var outer = mapper.toOuter(inner);
		
		assertNotNull(outer);
		assertFalse(outer.getSales().isEmpty());
	}
	
	@Test
	public void toOuterNullTest() {
		assertNull(mapper.toOuter(null));
	}
	
	@Test
	public void toOuterListNullTest() {
		var inner = MocksFactory.createSales();
		inner.setSales(null);
		
		var outer = mapper.toOuter(inner);
		
		assertNotNull(outer);
		assertTrue(outer.getSales().isEmpty());
	}
	
	@Test
	public void toOuterListEmptyTest() {
		var inner = MocksFactory.createSales();
		inner.getSales().clear();
		
		var outer = mapper.toOuter(inner);
		
		assertNotNull(outer);
		assertTrue(outer.getSales().isEmpty());
	}
}
