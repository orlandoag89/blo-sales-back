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
public class DebtorsMapperTest {
	
	@Autowired
	private DebtorsMapper mapper;

	@Test
	public void toOuterTest() {
		var inner = MocksFactory.createDebtors();
		
		var outer = mapper.toOuter(inner);
		
		assertNotNull(outer);
		assertFalse(outer.getDebtors().isEmpty());
	}
	
	@Test
	public void toOuterNullTest() {
		assertNull(mapper.toOuter(null));
	}
	
	@Test
	public void toOuterDebtorsNullTest() {
		var inner = MocksFactory.createDebtors();
		inner.setDebtors(null);
		
		var outer = mapper.toOuter(inner);
		
		assertNotNull(mapper);
		assertTrue(outer.getDebtors().isEmpty());
	}
	
	@Test
	public void toOuterDebtorsEmptyTest() {
		var inner = MocksFactory.createDebtors();
		inner.getDebtors().clear();
		
		var outer = mapper.toOuter(inner);
		
		assertNotNull(mapper);
		assertTrue(outer.getDebtors().isEmpty());
	}
}
