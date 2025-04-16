package com.blo.sales.facade.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blo.sales.factory.MocksFactory;

@SpringBootTest
public class DtoPartialPymentMapperTest {

	@Autowired
	private DtoPartialPymentMapper mapper;

	@Test
	public void toInnerTest() {
		var outer = MocksFactory.createDtoPartialPyment();

		var out = mapper.toInner(outer);

		assertNotNull(out);
		assertEquals(outer.getDate(), out.getDate());
		assertEquals(outer.getPartial_pyment(), out.getPartial_pyment());
	}

	@Test
	public void toInnerNullTest() {
		var out = mapper.toInner(null);

		assertNull(out);
	}

	@Test
	public void toOuterTest() {
		var outer = MocksFactory.createDtoIntPartialPyment();

		var out = mapper.toOuter(outer);

		assertNotNull(out);
		assertEquals(outer.getDate(), out.getDate());
		assertEquals(outer.getPartial_pyment(), out.getPartial_pyment());
	}
	
	@Test
	public void toOuterNullTest() {
		var out = mapper.toOuter(null);

		assertNull(out);
	}

}
