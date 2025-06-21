package com.blo.sales.facade.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blo.sales.factory.MocksFactory;

@SpringBootTest
public class DtoSaleMapperTest {
	
	@Autowired
	private DtoSaleMapper mapper;
	
	@Test
	public void toInnerTest() {
		var outer = MocksFactory.createDtoSaleNoCashbox();
		var out = mapper.toInner(outer);
		
		assertNotNull(out);
		assertEquals(outer.getClose_sale(), out.getClose_sale());
		assertEquals(outer.getId(), out.getId());
		assertEquals(outer.getOpen_date(), out.getOpen_date());
		assertEquals(outer.getTotal(), out.getTotal());
		assertEquals(outer.getProducts().size(), out.getProducts().size());
	}

	@Test
	public void toInnerNullTest() {
		var out = mapper.toInner(null);
		
		assertNull(out);
	}
	
	@Test
	public void toOuterTest() {
		var outer = MocksFactory.createDtoIntSaleNoCashbox();
		var out = mapper.toOuter(outer);
		
		assertNotNull(out);
		assertEquals(outer.getClose_sale(), out.getClose_sale());
		assertEquals(outer.getId(), out.getId());
		assertEquals(outer.getOpen_date(), out.getOpen_date());
		assertEquals(outer.getTotal(), out.getTotal());
		assertEquals(outer.getProducts().size(), out.getProducts().size());
	}
	
	@Test
	public void toOuterNullTest() {
		var out = mapper.toOuter(null);
		
		assertNull(out);
	}
}
