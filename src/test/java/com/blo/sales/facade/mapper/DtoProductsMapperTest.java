package com.blo.sales.facade.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blo.sales.factory.MocksFactory;

@SpringBootTest
public class DtoProductsMapperTest {

	@Autowired
	private DtoProductsMapper mapper;

	@Test
	public void toInnerTest() {
		var products = MocksFactory.createDtoProducts();

		var out = mapper.toInner(products);

		assertNotNull(out);
		assertFalse(out.getProducts().isEmpty());
		for (var i = 0; i < out.getProducts().size(); i++) {
			var itemInner = out.getProducts().get(i);
			var itemOuter = products.getProducts().get(i);

			assertEquals(itemOuter.getId(), itemInner.getId());
			assertEquals(itemOuter.getDesc(), itemInner.getDesc());
			assertEquals(itemOuter.getName(), itemInner.getName());
			assertEquals(itemOuter.getQuantity(), itemInner.getQuantity());
			assertEquals(itemOuter.getTotal_price(), itemInner.getTotal_price());
		}
	}

	@Test
	public void toInnerNullTest() {
		var out = mapper.toInner(null);

		assertNull(out);
	}

	@Test
	public void toInnerListEmptyTest() {
		var out = mapper.toInner(MocksFactory.createDtoProductsEmptyList());

		assertNotNull(out);
		assertNotNull(out.getProducts());
		assertTrue(out.getProducts().isEmpty());
	}

	@Test
	public void toInnerListNullTest() {
		var out = mapper.toInner(MocksFactory.createDtoProductsNullList());

		assertNotNull(out);
		assertNotNull(out.getProducts());
		assertTrue(out.getProducts().isEmpty());
	}
	
	@Test
	public void toOuterTest() {
		var products = MocksFactory.createDtoIntProducts();

		var out = mapper.toOuter(products);

		assertNotNull(out);
		assertFalse(out.getProducts().isEmpty());
		for (var i = 0; i < out.getProducts().size(); i++) {
			var itemInner = out.getProducts().get(i);
			var itemOuter = products.getProducts().get(i);

			assertEquals(itemOuter.getId(), itemInner.getId());
			assertEquals(itemOuter.getDesc(), itemInner.getDesc());
			assertEquals(itemOuter.getName(), itemInner.getName());
			assertEquals(itemOuter.getQuantity(), itemInner.getQuantity());
			assertEquals(itemOuter.getTotal_price(), itemInner.getTotal_price());
		}
	}
	
	@Test
	public void toOuterNullTest() {
		var out = mapper.toOuter(null);

		assertNull(out);
	}

	@Test
	public void toOuterListEmptyTest() {
		var out = mapper.toOuter(MocksFactory.createDtoIntProductsEmptyList());

		assertNotNull(out);
		assertNotNull(out.getProducts());
		assertTrue(out.getProducts().isEmpty());
	}

	@Test
	public void toOuterListNullTest() {
		var out = mapper.toOuter(MocksFactory.createDtoIntProductsNullList());

		assertNotNull(out);
		assertNotNull(out.getProducts());
		assertTrue(out.getProducts().isEmpty());
	}

}
