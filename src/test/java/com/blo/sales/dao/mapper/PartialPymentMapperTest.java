package com.blo.sales.dao.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blo.sales.factory.MocksFactory;

@SpringBootTest
public class PartialPymentMapperTest {
	
	@Autowired
	private PartialPymentMapper mapper;

	@Test
	public void toInnerTest() {
		var outer = MocksFactory.createDtoIntPartialPyment();
		
		var inner = mapper.toInner(outer);
		
		assertNotNull(inner);
		assertEquals(outer.getDate(), inner.getDate());
	}
		
	@Test
	public void toInnerNullInputTest() {
		var inner = mapper.toInner(null);
		
		assertNull(inner);
	}
	
	@Test
	public void toOuterTest() {
		var inner = MocksFactory.createPartialPyment();
		
		var outer = mapper.toOuter(inner);
		
		assertNotNull(outer);
		assertEquals(inner.getPartial_pyment(), outer.getPartial_pyment());
	}
	
	@Test
	public void toOuterNullTest() {
		var outer = mapper.toOuter(null);
		
		assertNull(outer);
	}
}
