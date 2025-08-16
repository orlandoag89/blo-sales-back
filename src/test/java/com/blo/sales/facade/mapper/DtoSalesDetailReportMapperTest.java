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
public class DtoSalesDetailReportMapperTest {

	@Autowired
	private DtoSalesDetailReportMapper mapper;
	
	@Test
	public void toOuterTest() {
		var outer = mapper.toOuter(MocksFactory.createDtoIntSalesDetailReport());
		
		assertNotNull(outer);
		assertFalse(outer.getSales().isEmpty());
	}
	
	@Test
	public void toOuterNullListTest() {
		var inner = MocksFactory.createDtoIntSalesDetailReport();
		inner.setSales(null);
		
		var outer = mapper.toOuter(inner);
		
		assertNotNull(outer);
		assertTrue(outer.getSales().isEmpty());
	}
	
	@Test
	public void toOuterEmptyListTest() {
		var inner = MocksFactory.createDtoIntSalesDetailReport();
		inner.getSales().clear();
		
		var outer = mapper.toOuter(inner);
		
		assertNotNull(outer);
		assertTrue(outer.getSales().isEmpty());
	}
	
	@Test
	public void toOuterNullTest() {
		var outer = mapper.toOuter(null);
		
		assertNull(outer);
	}
}
