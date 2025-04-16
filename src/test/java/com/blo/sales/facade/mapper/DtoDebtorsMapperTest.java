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
public class DtoDebtorsMapperTest {
	
	@Autowired
	private DtoDebtorsMapper mapper;
	
	@Test
	public void toInnerTest() {
		var debtors = MocksFactory.createDtoDebtors();
		
		var out = mapper.toInner(debtors);
		
		assertNotNull(out);
		assertFalse(out.getDebtors().isEmpty());
	}
	
	@Test
	public void toInnerEmptyListTest() {
		var debtors = MocksFactory.createDtoDebtors();
		debtors.getDebtors().clear();
		
		var out = mapper.toInner(debtors);
		
		assertNotNull(out);
		assertTrue(out.getDebtors().isEmpty());
	}
	
	@Test
	public void toInnerNullListTest() {
		var debtors = MocksFactory.createDtoDebtors();
		debtors.setDebtors(null);
		
		var out = mapper.toInner(debtors);
		
		assertNotNull(out);
		assertTrue(out.getDebtors().isEmpty());
	}
	
	@Test
	public void toInnerNullTest() {
		var out = mapper.toInner(null);
		
		assertNull(out);
	}
	
	@Test
	public void toOuterTest() {
		var debtors = MocksFactory.createDtoIntDebtors();
		
		var out = mapper.toOuter(debtors);
		
		assertNotNull(out);
		assertFalse(out.getDebtors().isEmpty());
	}
	
	@Test
	public void toOuterEmptyListTest() {
		var debtors = MocksFactory.createDtoIntDebtors();
		debtors.getDebtors().clear();
		
		var out = mapper.toOuter(debtors);
		
		assertNotNull(out);
		assertTrue(out.getDebtors().isEmpty());
	}
	
	@Test
	public void toOuterNullListTest() {
		var debtors = MocksFactory.createDtoIntDebtors();
		debtors.setDebtors(null);
		
		var out = mapper.toOuter(debtors);
		
		assertNotNull(out);
		assertTrue(out.getDebtors().isEmpty());
	}
	
	@Test
	public void toOuterNullTest() {
		var out = mapper.toOuter(null);
		
		assertNull(out);
	}

}
