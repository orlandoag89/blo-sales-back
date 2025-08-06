package com.blo.sales.dao.mapper;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blo.sales.factory.MocksFactory;

@SpringBootTest
public class CreditsMapperTest {

	@Autowired
	private CreditsMapper mapper;
	
	@Test
	public void toOuterTest() {
		var inner = MocksFactory.createCredits();
		
		var outer = mapper.toOuter(inner);
		
		assertFalse(outer.getCredits().isEmpty());
	}
	
	@Test
	public void toOuterNullTest() {
		var outer = mapper.toOuter(null);
		
		assertNull(outer);
	}
	
	@Test
	public void toOuterCreditsNullTest() {
		var inner = MocksFactory.createCredits();
		inner.setCredits(null);
		
		var outer = mapper.toOuter(inner);
		
		assertTrue(outer.getCredits().isEmpty());
	}
	
	@Test
	public void toOuterCreditsEmptyTest() {
		var inner = MocksFactory.createCredits();
		inner.setCredits(new ArrayList<>());
		
		var outer = mapper.toOuter(inner);
		
		assertTrue(outer.getCredits().isEmpty());
	}
}
