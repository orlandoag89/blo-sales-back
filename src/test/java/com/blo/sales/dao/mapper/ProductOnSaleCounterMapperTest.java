package com.blo.sales.dao.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blo.sales.factory.MocksFactory;

@SpringBootTest
public class ProductOnSaleCounterMapperTest {
	
	@Autowired
	private ProductOnSaleCounterMapper mapper;
	
	@Test
	public void toOuterTest() {
		var data = MocksFactory.createProductsOnSaleCounterItem();
		var out = mapper.toOuter(data);
		assertNotNull(data);
		assertEquals(data.getName(), out.getName());
	}

	@Test
	public void toOuterNullTest() {
		assertNull(mapper.toOuter(null));
	}
}
