package com.blo.sales.facade.mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blo.sales.factory.MocksFactory;

@SpringBootTest
public class DtoUserMapperTest {
	
	@Autowired
	private DtoUserMapper mapper;

	@Test
	public void toInnerTest() {
		var out = mapper.toInner(MocksFactory.createDtoCommonUser());
		
		assertNotNull(out);
	}
	
	@Test
	public void toInnerNullTest() {
		assertNull(mapper.toInner(null));
	}
	
	@Test
	public void toOuterTest() {
		var outer = mapper.toOuter(MocksFactory.createDtoIntCommonUser());
		
		assertNotNull(outer);
	}
	
	@Test
	public void toOuterNullTest() {
		assertNull(mapper.toOuter(null));
	}
}
