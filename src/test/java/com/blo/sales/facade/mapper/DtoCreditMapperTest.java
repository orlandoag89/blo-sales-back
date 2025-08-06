package com.blo.sales.facade.mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blo.sales.factory.MocksFactory;

@SpringBootTest
public class DtoCreditMapperTest {
	
	@Autowired
	private DtoCreditMapper mapper;

	@Test
	public void toInnerTest() {
		var out = mapper.toInner(MocksFactory.createDtoCreditSaved());
		
		assertNotNull(out);
	}
	
	@Test
	public void toInnerNullTest() {
		var out = mapper.toInner(null);
		
		assertNull(out);
	}
	
	@Test
	public void toOuterTest() {
		var out = mapper.toOuter(MocksFactory.createDtoInCreditSavedClose());
		
		assertNotNull(out);
	}
	
	@Test
	public void toOuterNullTest() {
		var out = mapper.toOuter(null);
		
		assertNull(out);
	}
}
