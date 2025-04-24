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
public class CashboxesMapperTest {
	
	@Autowired
	private CashboxesMapper mapper;

	@Test
	public void toInnerTest() {
		var outer = MocksFactory.createDtoIntCashboxes();
		
		var out = mapper.toInner(outer);
		
		assertNotNull(out);
		assertFalse(out.getBoxes().isEmpty());
	}
	
	@Test
	public void toInnerNullTest() {
		var out = mapper.toInner(null);
		
		assertNull(out);
	}
	
	@Test
	public void toInnerEmptyListTest() {
		var outer = MocksFactory.createDtoIntCashboxes();
		outer.getBoxes().clear();
		
		var out = mapper.toInner(outer);
		
		assertNotNull(out);
		assertTrue(out.getBoxes().isEmpty());
	}
	
	@Test
	public void toInnerNullListTest() {
		var outer = MocksFactory.createDtoIntCashboxes();
		outer.setBoxes(null);
		
		var out = mapper.toInner(outer);
		
		assertNotNull(out);
		assertTrue(out.getBoxes().isEmpty());
	}
	
	@Test
	public void toOuterTest() {
		var outer = MocksFactory.createCashboxes();
		
		var out = mapper.toOuter(outer);
		
		assertNotNull(out);
		assertFalse(out.getBoxes().isEmpty());
	}
	
	@Test
	public void toOuterNullTest() {
		var out = mapper.toOuter(null);
		
		assertNull(out);
	}
	
	@Test
	public void toOuterEmptyListTest() {
		var outer = MocksFactory.createCashboxes();
		outer.getBoxes().clear();
		
		var out = mapper.toOuter(outer);
		
		assertNotNull(out);
		assertTrue(out.getBoxes().isEmpty());
	}
	
	@Test
	public void toOuterNullListTest() {
		var outer = MocksFactory.createCashboxes();
		outer.setBoxes(null);
		
		var out = mapper.toOuter(outer);
		
		assertNotNull(out);
		assertTrue(out.getBoxes().isEmpty());
	}
}
