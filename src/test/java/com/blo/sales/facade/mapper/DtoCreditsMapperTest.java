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
public class DtoCreditsMapperTest {

	@Autowired
	private DtoCreditsMapper mapper;
	
	@Test
	public void toOuterTest() {
		var out = mapper.toOuter(MocksFactory.createDtoIntCredits());
		
		assertNotNull(out);
		assertFalse(out.getCredits().isEmpty());
	}
	
	@Test
	public void toOuterNullTest() {
		var out = mapper.toOuter(null);
		
		assertNull(out);
	}
	
	@Test
	public void toOuterNullListTest() {
		var inner = MocksFactory.createDtoIntCredits();
		inner.setCredits(null);
		
		var out = mapper.toOuter(inner);
		
		assertTrue(out.getCredits().isEmpty());
		
	}
	
	@Test
	public void toOuterEmptyListTest() {
		var inner = MocksFactory.createDtoIntCredits();
		inner.getCredits().clear();
		
		var out = mapper.toOuter(inner);
		
		assertTrue(out.getCredits().isEmpty());
		
	}
}
