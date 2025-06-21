package com.blo.sales.dao.mapper;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blo.sales.factory.MocksFactory;

@SpringBootTest
public class ProductsMapperTest {
	
	@Autowired
	private ProductsMapper mapper;

	@Test
	public void toInnerTest() {
		var outer = MocksFactory.createDtoIntProducts();
		
		var inner = mapper.toInner(outer);
		
		assertNotNull(inner);
		assertFalse(inner.getProducts().isEmpty());
	}
	
	@Test
	public void toInnerNullTest() {
		assertNull(mapper.toInner(null));
	}
	
	@Test
	public void toInnerProductsNullTest() {
		var outer = MocksFactory.createDtoIntProducts();
		outer.setProducts(null);
		
		var inner = mapper.toInner(outer);
		
		assertNotNull(inner);
		assertTrue(inner.getProducts().isEmpty());
	}
	
	@Test
	public void toInnerProductsEmptyTest() {
		var outer = MocksFactory.createDtoIntProducts();
		outer.getProducts().clear();
		
		var inner = mapper.toInner(outer);
		
		assertNotNull(inner);
		assertTrue(inner.getProducts().isEmpty());
	}
	
	@Test
	public void toOuterTest() {
		var inner = MocksFactory.createProducts();
		
		var outer = mapper.toOuter(inner);
		
		assertNotNull(outer);
		assertFalse(outer.getProducts().isEmpty());
	}
	
	@Test
	public void toOuterNullTest() {
		assertNull(mapper.toOuter(null));
	}
	
	@Test
	public void toOuterProductsNullTest() {
		var inner = MocksFactory.createProducts();
		inner.setProducts(null);
		
		var outer = mapper.toOuter(inner);
		
		assertNotNull(outer);
		assertTrue(outer.getProducts().isEmpty());
	}
	
	@Test
	public void toOuterProductsEmptyTest() {
		var inner = MocksFactory.createProducts();
		inner.getProducts().clear();
		
		var outer = mapper.toOuter(inner);
		
		assertNotNull(outer);
		assertTrue(outer.getProducts().isEmpty());
	}
}
