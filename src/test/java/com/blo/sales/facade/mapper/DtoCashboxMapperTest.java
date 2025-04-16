package com.blo.sales.facade.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blo.sales.factory.MocksFactory;

@SpringBootTest
public class DtoCashboxMapperTest {

	@Autowired
	private DtoCashboxMapper mapper;

	@Test
	public void toInnerTest() {
		var outer = MocksFactory.createDtoCashboxOpen();
		var out = mapper.toInner(outer);

		assertNotNull(out);
		assertEquals(outer.getId(), out.getId());
		assertEquals(outer.getDate(), out.getDate());
		assertEquals(outer.getMoney(), out.getMoney());
		assertEquals(outer.getStatus().name(), out.getStatus().name());
	}

	@Test
	public void toInnerInNullTest() {
		var out = mapper.toInner(null);

		assertNull(out);
	}

	@Test
	public void toOuterTest() {
		var inner = MocksFactory.createDtoIntCashboxOpen();
		var out = mapper.toOuter(inner);

		assertNotNull(out);
		assertEquals(inner.getId(), out.getId());
		assertEquals(inner.getDate(), out.getDate());
		assertEquals(inner.getMoney(), out.getMoney());
		assertEquals(inner.getStatus().name(), out.getStatus().name());
	}
	
	@Test
	public void toOuterOutNullTest() {
		var out = mapper.toOuter(null);
		
		assertNull(out);
	}
}
