package com.blo.sales.dao.mapper;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blo.sales.factory.MocksFactory;

@SpringBootTest
public class UsuarioMapperTest {

	@Autowired
	private UsuarioMapper mapper;
	
	@Test
	public void toInnerTest() {
		var inner = mapper.toInner(MocksFactory.createDtoIntCommonUser());
		
		assertNotNull(inner);
		assertFalse(inner.getPassword().isEmpty());
	}
	
	@Test
	public void toInnerNullTest() {
		assertNull(mapper.toInner(null));
	}
	
	@Test
	public void toOuterTest() {
		var outer = mapper.toOuter(MocksFactory.createCommonUsuarioWithoutOpenPasswordOpen());
		
		assertNotNull(outer);
	}
	
	@Test
	public void toOuterNullTest() {
		assertNull(mapper.toOuter(null));
	}
	
}
