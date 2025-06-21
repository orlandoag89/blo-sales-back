package com.blo.sales.facade.mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blo.sales.factory.MocksFactory;

@SpringBootTest
public class DtoUserTokenMapperTest {
	
	@Autowired
	private DtoUserTokenMapper mapper;

	@Test
	public void toOuterTest() {
		var inner = mapper.toOuter(MocksFactory.createDtoIntUserToken());
		
		assertNotNull(inner);
	}
	
	@Test
	public void toOuterNullTest() {
		assertNull(mapper.toOuter(null));
	}

}
