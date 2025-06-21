package com.blo.sales.dao.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blo.sales.factory.MocksFactory;

@SpringBootTest
public class CashboxMapperTest {
	
	@Autowired
	private CashboxMapper mapper;
	
	@Test
	public void toInnerTest() {
		var inner = MocksFactory.createDtoIntCashboxClose();
		
		var out = mapper.toInner(inner);
		
		assertNotNull(out);
		assertEquals(inner.getId(), out.getId());
		assertEquals(inner.getStatus().name(), out.getStatus().name());
	}
	
	@Test
	public void toInnerInputNullTest() {
		var out = mapper.toInner(null);
		
		assertNull(out);
	}
	
	@Test
	public void toOuterTest() {
		var outer = MocksFactory.createOpenCashbox();
		
		var out = mapper.toOuter(outer);
		
		assertNotNull(out);
		assertEquals(outer.getId(), out.getId());
		assertEquals(outer.getStatus().name(), out.getStatus().name());
	}
	
	@Test
	public void toOuterNullTest() {
		var out = mapper.toOuter(null);
		
		assertNull(out);
	}

}
