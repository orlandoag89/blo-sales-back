package com.blo.sales.facade.mapper;

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
public class DtoDebtorMapperTest {
	
	@Autowired
	private DtoDebtorMapper mapper;

	@Test
	public void toInnerDebtorExistTest() {
		var debtor = MocksFactory.createExistsDtoDebtor();
		
		var out = mapper.toInner(debtor);
		
		assertNotNull(out);
		assertNotNull(out.getId());
		assertEquals(debtor.getName(), out.getName());
		assertEquals(debtor.getOpen_date(), out.getOpen_date());
		assertEquals(debtor.getTotal(), out.getTotal());
		assertFalse(out.getPartial_pyments().isEmpty());
		assertFalse(out.getSales().isEmpty());	
	}
	
	@Test
	public void toInnerNullTest() {
		var out = mapper.toInner(null);
		
		assertNull(out);
	}
	
	@Test
	public void toInnerNewDebtorTest() {
		var debtor = MocksFactory.createNewDtoDebtor();
		
		var out = mapper.toInner(debtor);
		
		assertNotNull(out);
		assertNull(out.getId());
		assertEquals(debtor.getName(), out.getName());
		assertEquals(debtor.getOpen_date(), out.getOpen_date());
		assertEquals(debtor.getTotal(), out.getTotal());
		assertTrue(out.getPartial_pyments().isEmpty());
		assertTrue(out.getSales().isEmpty());	
	}
	
	@Test
	public void toInnerPartialPymentsListemptyTest() {
		var debtor = MocksFactory.createNewDtoDebtor();
		debtor.getPartial_pyments().clear();
		
		var out = mapper.toInner(debtor);
		
		assertNotNull(out);
		assertNull(out.getId());
		assertEquals(debtor.getName(), out.getName());
		assertEquals(debtor.getOpen_date(), out.getOpen_date());
		assertEquals(debtor.getTotal(), out.getTotal());
		assertTrue(out.getPartial_pyments().isEmpty());
		assertTrue(out.getSales().isEmpty());	
	}
	
	@Test
	public void toOuterNewDebtorTest() {
		var debtor = MocksFactory.createNewDtoIntDebtor();
		
		var out = mapper.toOuter(debtor);
		
		assertNotNull(out);
		assertNull(out.getId());
		assertEquals(debtor.getName(), out.getName());
		assertEquals(debtor.getOpen_date(), out.getOpen_date());
		assertEquals(debtor.getTotal(), out.getTotal());
		assertTrue(out.getPartial_pyments().isEmpty());
		assertTrue(out.getSales().isEmpty());	
	}
	
	@Test
	public void toOuterDebtorExistTest() {
		var debtor = MocksFactory.createExistsDtoIntDebtor();
		
		var out = mapper.toOuter(debtor);
		
		assertNotNull(out);
		assertNotNull(out.getId());
		assertEquals(debtor.getName(), out.getName());
		assertEquals(debtor.getOpen_date(), out.getOpen_date());
		assertEquals(debtor.getTotal(), out.getTotal());
		assertFalse(out.getPartial_pyments().isEmpty());
		assertFalse(out.getSales().isEmpty());	
	}
	
	@Test
	public void toOuterNullTest() {		
		var out = mapper.toOuter(null);
		
		assertNull(out);
	}
}
