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
public class DtoCashboxesMapperTest {

	@Autowired
	private DtoCashboxesMapper mapper;

	@Test
	public void toOuterTest() {
		var out = mapper.toOuter(MocksFactory.createDtoIntCashboxes());

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
		var out = mapper.toOuter(MocksFactory.createDtoIntCashboxesListEmpty());
		
		assertNotNull(out);
		assertTrue(out.getBoxes().isEmpty());
	}
	
	@Test
	public void toOuterNullListTest() {
		var out = mapper.toOuter(MocksFactory.createDtoIntCashboxesListNull());
		
		assertNotNull(out);
		assertTrue(out.getBoxes().isEmpty());
	}
}
