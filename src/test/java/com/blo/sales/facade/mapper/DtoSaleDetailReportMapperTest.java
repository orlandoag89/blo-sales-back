package com.blo.sales.facade.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blo.sales.factory.MocksFactory;

@SpringBootTest
public class DtoSaleDetailReportMapperTest {

	@Autowired
	private DtoSaleDetailReportMapper mapper;
	
	@Test
	public void toOuterTest() {
		var inner = MocksFactory.createDtoIntSaleDetailReport();
		
		var outer = mapper.toOuter(inner);
		
		assertNotNull(outer);
		assertEquals(inner.getTotalVentas(), outer.getTotalVentas());
	}
	
	@Test
	public void toOuterNullTest() {
		var outer = mapper.toOuter(null);
		
		assertNull(outer);
	}
}
