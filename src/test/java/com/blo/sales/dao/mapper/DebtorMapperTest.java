package com.blo.sales.dao.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blo.sales.factory.MocksFactory;

@SpringBootTest
public class DebtorMapperTest {
	
	@Autowired
	private DebtorMapper mapper;

	@Test
	public void toInnerTest() {
		var outer = MocksFactory.createExistsDtoIntDebtor();
		
		var inner = mapper.toInner(outer);
		
		assertNotNull(inner);
		assertEquals(inner.getId(), outer.getId());
		assertFalse(inner.getPartial_pyments().isEmpty());
		assertFalse(inner.getSales().isEmpty());
	}
	
	@Test
	public void toInnerNullTest() {
		assertNull(mapper.toInner(null));
	}
	
	@Test
	public void toInnerPartialPymentsSalesNullTest() {
		var outer = MocksFactory.createExistsDtoIntDebtor();
		outer.setPartial_pyments(null);
		outer.setSales(null);
		
		var inner = mapper.toInner(outer);
		
		assertNotNull(inner);
		assertEquals(inner.getId(), outer.getId());
		assertTrue(inner.getPartial_pyments().isEmpty());
		assertTrue(inner.getSales().isEmpty());
	}
	
	@Test
	public void toInnerPartialPymentsSalesEmptiesTest() {
		var outer = MocksFactory.createExistsDtoIntDebtor();
		outer.getPartial_pyments().clear();
		outer.getSales().clear();
		
		var inner = mapper.toInner(outer);
		
		assertNotNull(inner);
		assertEquals(inner.getId(), outer.getId());
		assertTrue(inner.getPartial_pyments().isEmpty());
		assertTrue(inner.getSales().isEmpty());
	}
	
	@Test
	public void toOuterTest() {
		var inner = MocksFactory.createExistsDebtor();
		
		var outer = mapper.toOuter(inner);
		
		assertNotNull(outer);
		assertEquals(inner.getId(), outer.getId());
		assertFalse(outer.getPartial_pyments().isEmpty());
		assertFalse(outer.getSales().isEmpty());
	}
	
	@Test
	public void toOuterNullTest() {
		assertNull(mapper.toOuter(null));
	}
	
	@Test
	public void toOuterPartialPymentsSalesNullTest() {
		var inner = MocksFactory.createExistsDebtor();
		inner.setPartial_pyments(null);
		inner.setSales(null);
		
		var outer = mapper.toOuter(inner);
		
		assertNotNull(outer);
		assertEquals(inner.getId(), outer.getId());
		assertTrue(outer.getPartial_pyments().isEmpty());
		assertTrue(outer.getSales().isEmpty());
	}
	
	@Test
	public void toOuterPartialPymentsSalesEmptyTest() {
		var inner = MocksFactory.createExistsDebtor();
		inner.getPartial_pyments().clear();
		inner.getSales().clear();
		
		var outer = mapper.toOuter(inner);
		
		assertNotNull(outer);
		assertEquals(inner.getId(), outer.getId());
		assertTrue(outer.getPartial_pyments().isEmpty());
		assertTrue(outer.getSales().isEmpty());
	}
}
