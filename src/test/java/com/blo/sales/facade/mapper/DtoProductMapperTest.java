package com.blo.sales.facade.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blo.sales.factory.MocksFactory;

@SpringBootTest
public class DtoProductMapperTest {
	
	@Autowired
	private DtoProductMapper mapper;
	
	@Test
	public void toInnerTest() {
		var product = MocksFactory.createDtoProduct();
		
		var out = mapper.toInner(product);
		
		assertEquals(product.getId(), out.getId());
		assertEquals(product.getDesc(), out.getDesc());
		assertEquals(product.getName(), out.getName());
		assertEquals(product.getQuantity(), out.getQuantity());
		assertEquals(product.getTotal_price(), out.getTotal_price());
	}

	@Test
	public void toInnerInputNullTest() {
		var out = mapper.toInner(null);
		assertNull(out);
	}
	
	@Test
	public void toOuterTest() {
		var innerProduct = MocksFactory.createDtoIntProduct();
		
		var out = mapper.toOuter(innerProduct);
		
		assertEquals(innerProduct.getId(), out.getId());
		assertEquals(innerProduct.getDesc(), out.getDesc());
		assertEquals(innerProduct.getName(), out.getName());
		assertEquals(innerProduct.getQuantity(), out.getQuantity());
		assertEquals(innerProduct.getTotal_price(), out.getTotal_price());
	}
	
	@Test
	public void toOuterInputNullTest() {
		var out = mapper.toOuter(null);
		assertNull(out);
	}
}
