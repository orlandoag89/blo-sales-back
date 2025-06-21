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
public class DtoSalesMapperTest {
	
	@Autowired
	private DtoSalesMapper mapper;

	@Test
	public void toInnerTest() {
		var outer = MocksFactory.createDtoSales();
		
		var out = mapper.toInner(outer);
		
		assertNotNull(out);
		assertFalse(out.getSales().isEmpty());
	}
	
	@Test
	public void toInnerNullTest() {
		var out = mapper.toInner(null);
		
		assertNull(out);
	}
	
	@Test
	public void toInnerListEmptyTest() {
		var out = mapper.toInner(MocksFactory.createDtoSalesListNull());
		
		assertNotNull(out);
		assertTrue(out.getSales().isEmpty());
	}
	
	@Test
	public void toInnerListNullTest() {
		var out = mapper.toInner(MocksFactory.createDtoSalesListNull());
		
		assertNotNull(out);
		assertTrue(out.getSales().isEmpty());
	}
	
	@Test
	public void toOutTest() {
		var out = mapper.toOuter(MocksFactory.createDtoIntSales());
		
		assertNotNull(out);
		assertFalse(out.getSales().isEmpty());
	}
	
	@Test
	public void toOuterNullTest() {
		var out = mapper.toOuter(null);
		
		assertNull(out);
	}
	
	@Test
	public void toOuterListEmpty() {
		var out = mapper.toOuter(MocksFactory.createDtoIntSalesListEmpty());
		
		assertNotNull(out);
		assertTrue(out.getSales().isEmpty());
	}
	
	@Test
	public void toOuterListNull() {
		var out = mapper.toOuter(MocksFactory.createDtoIntSalesListNull());
		
		assertNotNull(out);
		assertTrue(out.getSales().isEmpty());
	}
}
