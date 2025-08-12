package com.blo.sales.facade.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.blo.sales.business.ISalesBusiness;
import com.blo.sales.facade.mapper.DtoProductsOnSalesCounterMapper;
import com.blo.sales.factory.MocksFactory;
import com.blo.sales.factory.MocksUtils;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ReportsFacadeImplTest {
		
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ISalesBusiness salesBusiness;
	
	@MockBean
	private DtoProductsOnSalesCounterMapper productsOnSalesCounterMapper;
	
	@Test
	public void retrieveProductsOnSalesReportTest() throws Exception {
		Mockito.when(salesBusiness.getBestSellingProducts()).thenReturn(MocksFactory.createDtoIntProductsOnSalesCounter());
		Mockito.when(productsOnSalesCounterMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoProductsOnSalesCounter());
		
		var result = mockMvc.perform(get("/api/v1/reports/products-on-sales")
	    		.header(MocksUtils.X_TRACKING_ID, "retrieveProductsOnSalesReportTest")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andReturn();
		
		var productsOnSales = MocksUtils.getContentAsString(result, "retrieveProductsOnSalesReportTest");
		var objtProducts = MocksUtils.parserToCommonWrapper(productsOnSales,  MocksFactory.getReferenceFromDtoProductsOnSalesCounter());
		
		assertNotNull(objtProducts);
		assertFalse(objtProducts.getData().getProductsOnSales().isEmpty());
		assertNull(objtProducts.getError());
	}
}
